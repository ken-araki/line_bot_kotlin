package com.linebot.api.keep

import com.linebot.model.response.RestApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/self"])
class KeepAliveController {
    val log: Logger = LoggerFactory.getLogger(KeepAliveController::class.java)

    @GetMapping("/keep")
    fun index(): RestApiResponse {
        log.info("keep alive.")
        return RestApiResponse("success")
    }
}
