package com.linebot.action.qiita

import com.linebot.action.Action
import com.linebot.message.QiitaItemMessage
import com.linebot.model.qiita.QiitaRequestParameter
import com.linebot.service.qiita.QiitaService
import com.linecorp.bot.model.message.Message
import org.springframework.stereotype.Component

@Component
class QiitaItemAction(
    private val qiitaService: QiitaService,
    private val qiitaItemMessage: QiitaItemMessage
) : Action() {

    override fun execute(userId: String, message: String): List<Message> {
        val list = qiitaService.getItemByUserId(
                message, QiitaRequestParameter(perPage = 20, page = 1)
        )
        return listOf(qiitaItemMessage.build(message, list))
    }
}
