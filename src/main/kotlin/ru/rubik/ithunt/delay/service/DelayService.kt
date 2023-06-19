package ru.rubik.ithunt.delay.service

import org.springframework.stereotype.Service
import ru.rubik.ithunt.delay.model.Delay
import ru.rubik.ithunt.delay.request.DelayUpdateRequest

@Service
class DelayService {
    private var delay: Delay = Delay()
    fun getDelay(): Long {
        return delay.value
    }

    fun updateDelay(request: DelayUpdateRequest) {
        delay.value = request.delay
    }

    fun setState(delay: Delay) {
        this.delay = delay
    }

    fun getState(): Delay {
        return delay
    }
}