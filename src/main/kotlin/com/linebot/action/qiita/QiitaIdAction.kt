package com.linebot.action.qiita

import com.linebot.action.Action
import com.linebot.service.user.BotUserQiitaService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class QiitaIdAction(
        private val botUserQiitaService: BotUserQiitaService
) : Action() {

    override fun execute(userId: String, message: String): List<Message> {
        val botUserQiita = botUserQiitaService.updateOrCreate(userId, message)
        return listOf(TextMessage("Qiita名【${botUserQiita.qiitaUserId}】で設定しました"))
    }
}
