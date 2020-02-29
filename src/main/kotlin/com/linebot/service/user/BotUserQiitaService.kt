package com.linebot.service.user

import com.linebot.entity.BotUserQiita
import com.linebot.repository.BotUserQiitaRepository
import com.linebot.util.Utils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BotUserQiitaService(
        private val botUserQiitaRepository: BotUserQiitaRepository
) {

    @Transactional(readOnly = true)
    fun findByUserId(userId: String): BotUserQiita? {
        return botUserQiitaRepository.findByUserId(userId)
    }

    @Transactional
    fun updateOrCreate(userId: String, qiitaId: String): BotUserQiita {
        return findByUserId(userId)
                ?.takeIf { !it.qiitaUserId.isNullOrEmpty() }
                ?.let {
                    it.qiitaUserId = qiitaId
                    it.deleted = "0"
                    botUserQiitaRepository.save(it)
                }
                ?: botUserQiitaRepository.save(BotUserQiita(
                        userId = userId,
                        qiitaUserId = qiitaId,
                        deleted = "0",
                        createdDate = Utils.now()
                ))
    }

    @Transactional
    fun delete(userId: String): BotUserQiita? {
        return findByUserId(userId)
                ?.takeIf { !it.qiitaUserId.isNullOrEmpty() }
                ?.let {
                    it.deleted = "1"
                    botUserQiitaRepository.save(it)
                }
    }
}
