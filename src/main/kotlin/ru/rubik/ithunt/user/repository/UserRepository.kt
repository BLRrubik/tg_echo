package ru.rubik.ithunt.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.rubik.ithunt.user.entity.User
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    fun findByTelegramId(telegramId: Long): Optional<User>
}