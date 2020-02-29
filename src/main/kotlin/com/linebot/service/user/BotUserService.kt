package com.linebot.service.user

import com.linebot.entity.BotUser
import com.linebot.repository.BotUserRepository
import com.linebot.util.Utils
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.validation.constraints.NotNull

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
}
