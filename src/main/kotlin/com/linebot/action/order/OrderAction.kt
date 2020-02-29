package com.linebot.action.order

import com.linebot.action.Action
import com.linebot.entity.Request
import com.linebot.service.request.RequestService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull
import java.util.Collections

@Component
class OrderAction(
        private val requestService: RequestService
) : Action() {

    override fun execute(userId: String, message: String): List<Message> {
        Request(
                userId = userId,
                request = message,
                status = 0,
                deleted = "0",
                createdAt = Utils.now()
        ).apply {
            requestService.insert(this)
        }
        return listOf(TextMessage("ご意見ありがとうございます！"))
    }
}
