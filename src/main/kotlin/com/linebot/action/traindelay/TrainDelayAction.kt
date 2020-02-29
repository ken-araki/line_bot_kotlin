package com.linebot.action.traindelay

import com.linebot.action.Action
import com.linebot.service.notice.NoticeService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull

@Component
class TrainDelayAction(
        private val noticeService: NoticeService
) : Action() {

    override fun execute(@NotNull userId: String, @NotNull message: String): List<Message> {
        val time = TrainDelayTime.values()
                .first { it.time == message }

        noticeService.insertOrUpdateTrainDelay(userId, time)
        val replyMessage = "${time.hour}時${time.minute}分に電車遅延通知を設定しました"
        return listOf(TextMessage(replyMessage))
    }

    override fun check(message: String): Boolean {
        return message in TrainDelayTime.values().map { it.time }
    }
}
