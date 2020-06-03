package com.linebot.api.tobuy

import com.linebot.api.response.ListResponse
import com.linebot.api.response.SuccessResponse
import com.linebot.api.response.TobuyDto
import com.linebot.config.PropertiesConfig
import com.linebot.service.tobuy.TobuyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/tobuy"])
class TobuyController(
    private val tobuyService: TobuyService,
    private val propertiesConfig: PropertiesConfig
) {
    val log: Logger = LoggerFactory.getLogger(TobuyController::class.java)
    @GetMapping
    fun get(
        @RequestHeader("token") token: String,
        @RequestHeader("nonce") nonce: String
    ): ListResponse<TobuyDto> {
        if (token == propertiesConfig.token && nonce.isEmpty()) {
            throw RuntimeException()
        }
        val list = propertiesConfig.ownUserMid?.let { mid ->
            tobuyService.findByIsCompleted(mid, "0")
                .map { TobuyDto(it.id!!, it.goods!!) }
        } ?: emptyList()

        return ListResponse(list)
    }

    @PostMapping(path = ["/add"])
    fun add(
        @RequestHeader("token") token: String,
        @RequestHeader("nonce") nonce: String,
        @RequestParam(name = "goods") goods: String
    ): SuccessResponse {
        if (token == propertiesConfig.token && nonce.isEmpty() && goods.isEmpty()) {
            throw RuntimeException()
        }
        tobuyService.insertByGoods(propertiesConfig.ownUserMid!!, goods)
        return SuccessResponse()
    }

    @PostMapping(path = ["/buy"])
    fun add(
        @RequestHeader("token") token: String,
        @RequestHeader("nonce") nonce: String,
        @RequestParam(name = "ids") ids: List<Int>
    ): SuccessResponse {
        if (token == propertiesConfig.token && nonce.isEmpty() && ids.isEmpty()) {
            throw RuntimeException()
        }
        tobuyService.updateCompletedById(ids)
        return SuccessResponse()
    }
}
