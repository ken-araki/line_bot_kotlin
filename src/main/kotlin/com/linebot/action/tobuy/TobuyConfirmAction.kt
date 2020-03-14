package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linebot.service.tobuy.TobuyService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class TobuyConfirmAction(
        private val tobuyService: TobuyService
) : Action() {

    override fun execute(userId: String, message: String): List<Message> {
        val tobuyList = tobuyService.findByIsCompleted(userId, "0")
                .joinToString { Utils.LINE_SEPARATOR + it.goods }
        return listOf(TextMessage("買い物リストはこちらです。$tobuyList"))
    }
}
