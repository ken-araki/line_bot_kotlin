package com.linebot.repository

import com.linebot.entity.BotUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BotUserRepository : JpaRepository<BotUser, Long> {
    fun findByDeleted(deleted: String): List<BotUser>
    fun findByUserId(userId: String): BotUser?
    fun findByAppUserIdAndDeleted(appUserId: String, deleted: String): BotUser?
}
