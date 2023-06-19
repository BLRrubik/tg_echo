package ru.rubik.ithunt.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class User(
    @Column(name = "telegram_id")
    private val telegramId: Long,

    @Column(name = "username")
    private val username: String
) {
    @Id
    @Column(name = "user_id")
    private val id: UUID = UUID.randomUUID()
}