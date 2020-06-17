package com.linebot.service.user

import com.linebot.entity.BotUser
import com.linebot.repository.BotUserRepository
import com.linebot.util.RandomUtils
import com.linebot.util.Utils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BotUserService(
    private val botUserRepository: BotUserRepository
) {

    @Transactional(readOnly = true)
    fun findActiveUser(): List<BotUser> {
        return botUserRepository.findByDeleted("0")
    }

    @Transactional
    fun insert(userId: String): BotUser {
        return botUserRepository.save(BotUser(
                userId = userId,
                appUserId = RandomUtils.createUserId(),
                deleted = "0",
                createdDate = Utils.now()
        ))
    }

    @Transactional
    fun delete(userId: String): BotUser? {
        return botUserRepository.findByUserId(userId)?.let {
            it.deleted = "1"
            botUserRepository.save(it)
        }
    }

    @Transactional
    fun getOrCreate(userId: String): BotUser {
        return botUserRepository.findByUserId(userId) ?: insert(userId)
    }
}
