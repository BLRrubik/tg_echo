package ru.rubik.ithunt.message.model.mapper

import ru.rubik.ithunt.message.model.Message
import ru.rubik.ithunt.message.model.dto.MessageDto

class MessageMapper {
    companion object {
        fun toDto(message: Message): MessageDto {
            return MessageDto(
                message.user.username,
                message.text
            )
        }
    }
}