package ru.rubik.ithunt.message.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rubik.ithunt.message.model.dto.MessageDto
import ru.rubik.ithunt.message.service.MessageService
import java.util.*


@RestController
@RequestMapping("/message")
class MessageController @Autowired constructor(
    private val messageService: MessageService
) {
    @GetMapping("/last/{userId}")
    fun getUserLastMessage(@PathVariable("userId") userId: UUID): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(messageService.getLastUserMessage(userId))
    }
}