package ru.rubik.ithunt.bot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfig(
    @Value("\${bot.name}") val name: String,
    @Value("\${bot.key}") val key: String
)
