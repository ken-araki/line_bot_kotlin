package com.linebot.api.retroactive

import com.linebot.api.response.SuccessResponse
import com.linebot.repository.BotUserRepository
import com.linebot.util.RandomUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/retroactive/001"])
class RetroactiveUserIdController(
    private val botUserRepository: BotUserRepository
) {
    /**
     * 新たにこのアプリケーション用にUSER IDを設けることにした。
     * 既存ユーザにはIDが存在していないため、このバッチを実行し遡及する
     */
    @GetMapping("/userId")
    fun index(): SuccessResponse {
        botUserRepository.findAll()
            .filter { it.appUserId.isNullOrEmpty() }
            .forEach {
                it.appUserId = RandomUtils.createUserId()
                botUserRepository.save(it)
            }
        return SuccessResponse()
    }
}
