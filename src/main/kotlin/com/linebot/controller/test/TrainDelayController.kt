package com.linebot.controller.test

import com.linebot.client.train.TrainDelayClient
import com.linebot.model.train.TrainDelay
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/test/train"])
@Profile("local")
class TrainDelayController(
        private val client: TrainDelayClient
) {

    @GetMapping("/delay")
    fun get(): List<TrainDelay> {
        return client.delay
    }
}
