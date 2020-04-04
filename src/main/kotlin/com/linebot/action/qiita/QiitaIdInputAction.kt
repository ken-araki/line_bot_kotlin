package com.linebot.action.qiita

import com.linebot.action.Action
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class QiitaIdInputAction : Action() {

    override var nextAction: String? = "qiitaItemAction"

    override fun execute(userId: String, message: String): List<Message> {
        return listOf(TextMessage("QiitaのUser名を入力してください"))
    }
}
