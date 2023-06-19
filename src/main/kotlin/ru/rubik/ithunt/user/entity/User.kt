package ru.rubik.ithunt.user.entity

import ru.rubik.ithunt.message.model.dto.MessageDto
import java.util.UUID

data class User(
    val username: String
) {
    val id: UUID = UUID.randomUUID()
    var messageCount: Long = 0
    var lastMessage: MessageDto? = null
}