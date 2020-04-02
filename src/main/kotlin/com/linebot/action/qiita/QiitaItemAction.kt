package com.linebot.action.qiita

import com.linebot.action.Action
import com.linebot.message.QiitaItemMessage
import com.linebot.model.qiita.QiitaRequestParameter
import com.linebot.service.qiita.QiitaService
import com.linebot.service.user.BotUserQiitaService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class QiitaItemAction(
        private val botUserQiitaService: BotUserQiitaService,
        private val qiitaService: QiitaService,
        private val qiitaItemMessage: QiitaItemMessage
) : Action() {

    override fun execute(userId: String, message: String): List<Message> {
        return botUserQiitaService.findByUserId(userId)?.qiitaUserId
                ?.let {
                    val list = qiitaService.getItemByUserId(
                            it, QiitaRequestParameter(perPage = 20, page = 1)
                    )
                    listOf(qiitaItemMessage.build(it, list))
                }
                ?: listOf(TextMessage("Qiita名を設定してください"))
    }
}
