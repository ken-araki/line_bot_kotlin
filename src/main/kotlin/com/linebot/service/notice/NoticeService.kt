package com.linebot.service.notice

import com.linebot.action.traindelay.TrainDelayTime
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
    fun insertOrUpdateTrainDelay(userId: String, time: TrainDelayTime): Notice {
        val notice = noticeRepository.findByUserIdAndType(userId, Type.TRAIN_DELAY.code)
                .firstOrNull()
                ?: Notice(
                        userId = userId,
                        year = 0,
                        month = 0,
                        day = 0,
                        dayOfWeek = 0,
                        minute = time.minute,
                        type = Notice.Type.TRAIN_DELAY.code,
                        deleted = "0",
                        createdAt = Utils.now()
                )
        notice.hour = time.hour
        notice.minute = time.minute
        return noticeRepository.save(notice)
    }

    @Transactional(readOnly = true)
    fun findTrainDelay(): List<Notice> {
        return noticeRepository.findByTypeAndDeleted(Type.TRAIN_DELAY.code, "0")
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
