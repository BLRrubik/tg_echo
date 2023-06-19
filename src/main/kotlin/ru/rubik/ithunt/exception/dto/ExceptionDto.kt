package ru.rubik.ithunt.exception.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExceptionDto(
    var status: HttpStatus?,
    var message: String?,
    var stacktrace: StackTraceElement,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    var timestamp: LocalDateTime?
)