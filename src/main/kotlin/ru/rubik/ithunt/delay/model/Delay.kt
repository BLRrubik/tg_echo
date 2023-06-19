package ru.rubik.ithunt.delay.model


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class Delay (
    @Value("\${messages.delay}") var value: Long = 0
)