package ru.rubik.ithunt.message.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rubik.ithunt.message.model.dto.MessageDto
import ru.rubik.ithunt.user.service.UserService
import java.util.UUID

@Service
class MessageService @Autowired constructor(
    private val userService: UserService
) {
    fun getLastUserMessage(userId: UUID): MessageDto? {
        val user = userService.findUserById(userId)

        return userService.getUserLastMessage(user)
    }
}