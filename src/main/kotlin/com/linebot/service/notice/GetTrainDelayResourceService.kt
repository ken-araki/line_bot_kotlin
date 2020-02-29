package com.linebot.service.notice

import com.linebot.client.train.TrainDelayClient
import com.linebot.service.log.LogService
import com.linebot.service.message.PushMessageService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class GetTrainDelayResourceService(
        private val pushMessageService: PushMessageService,
        private val trainDelayClient: TrainDelayClient,
        private val noticeService: NoticeService,
        private val logService: LogService
) {

    fun execute() {
        val now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"))
        val hour = now.hour
        val minute = now.minute

        val sb = StringBuilder(128)
        val results = trainDelayClient.delay
        results.takeIf { it.isNotEmpty() }
                ?.filter { it.name.isNullOrEmpty() }
                ?.forEach { sb.append(Utils.LINE_SEPARATOR).append(it.name) }
                ?: sb.append("遅延している沿線はありません。")

        logService.insertTrainDelay(results)
        val userIds = noticeService.findTrainDelay()
                .filter { it.hour == hour && it.minute == minute && !it.userId.isNullOrEmpty() }
                .map { it.userId!! }
                .toSet()
        if (userIds.isNotEmpty()) {
            pushMessageService.multicast(userIds, listOf(TextMessage(sb.toString())))
        }
    }
}
