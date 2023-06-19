package ru.rubik.ithunt.user.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rubik.ithunt.user.request.UserSaveRequest
import ru.rubik.ithunt.user.service.UserService

@RestController
@RequestMapping("/api/v1/users")
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping()
    fun saveUser(@RequestBody request: UserSaveRequest) {
        userService.saveUser(request)
    }
}