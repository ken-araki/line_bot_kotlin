package com.linebot.action.traindelay

import com.linebot.action.Action
import com.linebot.message.TrainDelayTimeMessage
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class TrainDelayInputAction : Action() {

    override var nextAction: String? = "trainDelayAction"
    override fun execute(userId: String, message: String): List<Message> {
        val alt = "電車遅延通知する時間を選択してください"
        return listOf(
                TextMessage(alt),
                TrainDelayTimeMessage().build(alt)
        )
    }
}
