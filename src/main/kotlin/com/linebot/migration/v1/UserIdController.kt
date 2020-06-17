package com.linebot.migration.v1

import com.linebot.repository.BotUserRepository
import com.linebot.util.RandomUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/migration"])
class UserIdController(private val botUserRepository: BotUserRepository) {
    @PostMapping("/appUserId")
    fun migrate(): List<String> {
        return botUserRepository.findAll().map { user ->
            RandomUtils.createUserId().also {
                user.appUserId = it
                botUserRepository.save(user)
            }
        }
    }
}
