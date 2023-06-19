package ru.rubik.ithunt.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.rubik.ithunt.bot.config.BotConfig
import ru.rubik.ithunt.delay.service.DelayService
import ru.rubik.ithunt.message.model.Message
import ru.rubik.ithunt.user.request.UserSaveRequest
import ru.rubik.ithunt.user.service.UserService
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Component
class TelegramBot @Autowired constructor(
    private val config: BotConfig,
    private val userService: UserService,
    private val delayService: DelayService
) : TelegramLongPollingBot() {
    private val messageHandler: MessageHandler = MessageHandler()

    init {
        messageHandler.start()
    }
    override fun getBotUsername(): String {
        return "mybot"
    }

    override fun getBotToken(): String {
        return config.key
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text

            when(messageText) {
                "/start" -> saveUser(update)
                else -> processEcho(update)
            }
        }
    }

    private fun processEcho(update: Update) {
        val userOpt =
            userService.findUserByUsername(update.message.from.userName ?: update.message.from.id.toString())

        if (!userOpt.isPresent) {
            prepareAndSendResponse(update, "Register by passing /start command")
            return
        }

        val user = userOpt.get()

        val message = Message(
            update.message.text,
            user,
            update
        )

        messageHandler.addMessage(message)
    }

    private fun saveUser(update: Update) {
        val request = UserSaveRequest(
            update.message.from.id,
            update.message.from.userName ?: update.message.from.id.toString()
        )

        userService.saveUser(request)

        prepareAndSendResponse(update, "Hello from bot")
    }

    private fun prepareAndSendResponse(update: Update, textToSend: String) {
        val message = SendMessage()
        message.replyToMessageId = update.message.messageId
        message.chatId = update.message.chatId.toString()
        message.text = textToSend
        executeMessage(message)
    }

    private fun executeMessage(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            println(e.message)
        }
    }

    override fun onClosing() {
        super.onClosing()
        messageHandler.stopHandler()
    }

    inner class MessageHandler: Thread() {
        private val messageQueue: BlockingQueue<Message> = LinkedBlockingQueue()
        private var running = true

        override fun run() {
            while (running) {
                try {
                    val message = messageQueue.take()
                    val userOpt = userService.findUserByUsername(message.user.username)

                    if (!userOpt.isPresent) {
                        return
                    }

                    var user = userOpt.get()

                    user = userService.updateUserMessageCount(user)!!
                    user = userService.updateUserLastMessage(user, message)!!

                    message.text = "${message.text} ${user.messageCount}"

                    sleep(delayService.getDelay())

                    prepareAndSendResponse(message.update, message.text)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        fun addMessage(message: Message) {
            messageQueue.add(message)
        }

        fun stopHandler() {
            running = false
            interrupt()
        }
    }
}