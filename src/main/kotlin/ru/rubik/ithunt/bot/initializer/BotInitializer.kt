package ru.rubik.ithunt.bot.initializer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.rubik.ithunt.bot.TelegramBot

@Component
class BotInitializer @Autowired constructor(
    private val bot: TelegramBot
) {

    @EventListener(ContextRefreshedEvent::class)
    @Throws(TelegramApiException::class)
    fun init() {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        try {
            telegramBotsApi.registerBot(bot)
        } catch (e: TelegramApiException) {
            println("Error occurred: " + e.message)
        }
    }
}
