package com.linebot.service.notice

import com.linebot.client.train.TrainDelayClient
import com.linebot.service.message.PushMessageService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.TextMessage
import java.time.LocalDateTime
import java.time.ZoneId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetTrainDelayResourceService(
    private val pushMessageService: PushMessageService,
    private val trainDelayClient: TrainDelayClient,
    private val noticeService: NoticeService
) {

    @Transactional
    fun execute() {
        val now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"))
        val hour = now.hour
        val minute = now.minute
        val results = trainDelayClient.delay
        val message = results.takeIf { it.isNotEmpty() }
            ?.filterNot { it.name.isNullOrEmpty() }
            ?.map { it.name }
            ?.joinToString(Utils.LINE_SEPARATOR)
            ?: "遅延している沿線はありません。"
        val userIds = noticeService.findTrainDelay()
            .filter { it.hour == hour && it.minute == minute }
            .mapNotNull { it.userId }
            .toSet()

        if (userIds.isNotEmpty()) {
            pushMessageService.multicast(userIds, listOf(TextMessage(message)))
        }
    }
}
