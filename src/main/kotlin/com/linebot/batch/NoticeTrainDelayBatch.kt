package com.linebot.batch

import com.linebot.service.notice.GetTrainDelayResourceService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
class NoticeTrainDelayBatch(
        private val getTrainDelayResourceService: GetTrainDelayResourceService
) {

    @Scheduled(cron = "0 */20 7-9 * * *", zone = "Asia/Tokyo")
    fun execute() {
        getTrainDelayResourceService.execute()
    }
}
