package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class TobuyAddInputAction : Action() {

    override var nextAction: String? = "tobuyAddAction"

    override fun execute(userId: String, message: String): List<Message> {
        return listOf(TextMessage("改行区切りで買い物リストを登録してください"))
    }
}
