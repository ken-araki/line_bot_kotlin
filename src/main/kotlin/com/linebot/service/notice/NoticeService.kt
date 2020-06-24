package com.linebot.service.notice

import com.linebot.entity.Notice
import com.linebot.entity.Notice.Type
import com.linebot.repository.NoticeRepository
import com.linebot.util.Utils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository
) {
    @Transactional
    fun insertOrUpdate(userId: String, hour: Int, minute: Int): Notice {
        val notice = noticeRepository.findByUserIdAndType(userId, Type.NORMAL.code)
            .firstOrNull()
            ?: Notice(
                userId = userId,
                year = 0,
                month = 0,
                day = 0,
                dayOfWeek = 0,
                type = Type.NORMAL.code,
                deleted = "0",
                createdAt = Utils.now()
            )
        notice.apply {
            this.hour = hour
            this.minute = minute
        }
        return noticeRepository.save(notice)
    }

    @Transactional
    fun deleteNotice(userId: String) {
        noticeRepository.findByUserIdAndDeleted(userId, "0")
            .forEach {
                it.deleted = "1"
                noticeRepository.save(it)
            }
    }
}
