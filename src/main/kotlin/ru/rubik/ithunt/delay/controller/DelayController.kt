package ru.rubik.ithunt.delay.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rubik.ithunt.delay.request.DelayUpdateRequest
import ru.rubik.ithunt.delay.service.DelayService

@RestController
@RequestMapping("/delay")
class DelayController @Autowired constructor(
    private val delayService: DelayService
) {
    @PutMapping("/update")
    fun updateDelay(@RequestBody request: DelayUpdateRequest) {
        delayService.updateDelay(request)
    }
}