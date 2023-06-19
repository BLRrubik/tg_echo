package ru.rubik.ithunt.state

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.rubik.ithunt.delay.model.Delay
import ru.rubik.ithunt.delay.service.DelayService
import ru.rubik.ithunt.user.entity.User
import ru.rubik.ithunt.user.service.UserService
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths
import java.util.*
import java.util.logging.Logger
import kotlin.math.log


@Component
class ApplicationState @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val userService: UserService,
    private val delayService: DelayService,
) {
    private val logger: Logger = Logger.getLogger(ApplicationState::class.java.name)
    private var state: State = State()
    init {
        try {
            val json = String(Files.readAllBytes(Paths.get("/state/state.json")))

            state = objectMapper.readValue(
                json,
                State::class.java
            )

        } catch (e: NoSuchFileException) {
            logger.info("Failed to read state. Initializing new state")
        } finally {
            userService.setState(state.users)
            delayService.setState(state.delay)
        }
    }

    @PreDestroy
    fun saveState() {
        state.users = userService.getState()
        state.delay = delayService.getState()

        val json = objectMapper.writeValueAsString(state)

        FileWriter("/state/state.json").use { writer -> writer.write(json) }

    }
}

@Component
class State {
    var users: MutableMap<UUID, User> = mutableMapOf()
    var delay: Delay = Delay()
}