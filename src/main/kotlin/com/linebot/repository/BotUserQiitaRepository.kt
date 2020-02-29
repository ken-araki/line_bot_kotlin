package com.linebot.repository

import com.linebot.entity.BotUserQiita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface BotUserQiitaRepository : JpaRepository<BotUserQiita, Long> {
    fun findByUserId(userId: String): BotUserQiita?
}
