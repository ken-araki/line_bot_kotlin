package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linebot.message.FlexMessageBuilder
import com.linebot.service.tobuy.TobuyService
import com.linecorp.bot.model.action.MessageAction
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component

@Component
class TobuyComplateInputAction(
        private val tobuyService: TobuyService,
        private val flexMessageBuilder: FlexMessageBuilder
) : Action() {

    override var nextAction: String? = "tobuyComplateAction"

    override fun execute(userId: String, message: String): List<Message> {
        val tobuyList = tobuyService.findByIsCompleted(userId, "0")
        if (tobuyList.isEmpty()) {
            return listOf(TextMessage("買い物リストはありません"))
        }
        val list = tobuyList
                .map {
                    MessageAction(
                            it.goods,
                            "${it.id} ${it.goods}"
                    )
                }
        return listOf(TextMessage(
                "購入した商品を選択してください"),
                flexMessageBuilder.buildListMessageAction("購入した商品を選択してください", list)
        )
    }
}
