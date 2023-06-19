package ru.rubik.ithunt.user.request

data class UserSaveRequest(
    val telegramId: Long,
    val username: String
)