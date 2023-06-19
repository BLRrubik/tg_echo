package ru.rubik.ithunt.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.rubik.ithunt.exception.NotFoundException
import ru.rubik.ithunt.exception.dto.ExceptionDto
import java.time.LocalDateTime

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: NotFoundException): ResponseEntity<ExceptionDto> {
        return ResponseEntity(
            ExceptionDto(
                HttpStatus.NOT_FOUND,
                e.message,
                e.stackTrace[0],
                LocalDateTime.now()
            ),
            HttpStatus.NOT_FOUND
        )
    }
}