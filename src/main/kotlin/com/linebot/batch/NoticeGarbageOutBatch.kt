package com.linebot.batch

import com.linebot.service.notice.NoticeGarbageOutService
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
@Profile("release", "local", "develop")
class NoticeGarbageOutBatch(
    private val noticeGarbageOutService: NoticeGarbageOutService
) {

    @Scheduled(cron = "0 20 21 * * 4", zone = "Asia/Tokyo")
    fun execute() {
        noticeGarbageOutService.executeDayBefore()
    }
}
