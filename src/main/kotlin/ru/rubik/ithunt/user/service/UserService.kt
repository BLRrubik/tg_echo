package ru.rubik.ithunt.user.service

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Service
import ru.rubik.ithunt.exception.NotFoundException
import ru.rubik.ithunt.message.model.Message
import ru.rubik.ithunt.message.model.dto.MessageDto
import ru.rubik.ithunt.message.model.mapper.MessageMapper
import ru.rubik.ithunt.user.entity.User
import ru.rubik.ithunt.user.request.UserSaveRequest
import java.util.*


@Service
@EnableScheduling
class UserService {
    private var users: MutableMap<UUID, User> = mutableMapOf()

    fun saveUser(request: UserSaveRequest) {
        if (findUserByUsername(request.username).isPresent) {
            return
        }

        val user = User (
            request.username
        )

        users[user.id] = user
    }

    fun getUserLastMessage(user: User): MessageDto? {
        return user.lastMessage
    }

    fun findUserById(userId: UUID): User {
        return users[userId] ?: throw NotFoundException("User with id {${userId}} is not found")
    }

    fun findUserByUsername(username: String): Optional<User> {
        return users.values.stream()
            .filter {u -> u.username == username}
            .findFirst()
    }

    fun updateUserMessageCount(user: User): User? {
        user.messageCount++

        users[user.id] = user

        return user
    }

    fun updateUserLastMessage(user: User, message: Message): User? {
        user.lastMessage = MessageMapper.toDto(message)

        users[user.id] = user

        return user
    }

    fun setState(users: MutableMap<UUID, User>) {
        this.users = users
    }

    fun getState(): MutableMap<UUID, User> {
        return users
    }
}