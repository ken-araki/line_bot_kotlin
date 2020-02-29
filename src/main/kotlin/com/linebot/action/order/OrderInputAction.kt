package com.linebot.action.order

import com.linebot.action.Action
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull
import java.util.Collections

@Component
class OrderInputAction : Action() {

    override var nextAction: String? = "orderAction"

    override fun execute(userId: String, message: String): List<Message> {
        return listOf(TextMessage("要望・バグを記入してください"))
    }
}
