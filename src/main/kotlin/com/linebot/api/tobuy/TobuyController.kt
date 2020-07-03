package com.linebot.api.tobuy

import com.linebot.api.response.ListResponse
import com.linebot.api.response.SuccessResponse
import com.linebot.api.response.TobuyDto
import com.linebot.config.PropertiesConfig
import com.linebot.security.user.AppUser
import com.linebot.service.tobuy.TobuyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
    fun get(@AuthenticationPrincipal appUser: AppUser): ListResponse<TobuyDto> {
        val list = appUser.appUserId.let { mid ->
            tobuyService.findByIsCompleted(mid, "0")
                .map { TobuyDto(it.id!!, it.goods!!) }
        }

        return ListResponse(list)
    }

    @PostMapping(path = ["/add"])
    fun add(
        @AuthenticationPrincipal appUser: AppUser,
        @RequestParam(name = "goods") goods: String
    ): SuccessResponse {
        tobuyService.insertByGoods(appUser.appUserId, goods)
        return SuccessResponse()
    }

    @PostMapping(path = ["/buy"])
    fun add(
        @AuthenticationPrincipal appUser: AppUser,
        @RequestBody ids: List<Int>
    ): SuccessResponse {
        tobuyService.updateCompletedById(ids)
        return SuccessResponse()
    }
}
