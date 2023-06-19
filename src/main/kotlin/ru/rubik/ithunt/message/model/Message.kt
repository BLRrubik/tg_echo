package ru.rubik.ithunt.message.model

import org.telegram.telegrambots.meta.api.objects.Update
import ru.rubik.ithunt.user.entity.User


data class Message(
    var text: String,
    val user: User,
    val update: Update
)