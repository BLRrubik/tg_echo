package ru.rubik.ithunt.user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rubik.ithunt.user.entity.User
import ru.rubik.ithunt.user.repository.UserRepository
import ru.rubik.ithunt.user.request.UserSaveRequest

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun saveUser(request: UserSaveRequest) {
        if (userRepository.findByTelegramId(request.telegramId).isPresent) {
            return
        }

        val user = User (
            request.telegramId,
            request.username
        )

        userRepository.save(user)
    }
}