package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linebot.service.tobuy.TobuyService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.validation.constraints.NotNull

@Component
class TobuyComplateAction(
        private val tobuyService: TobuyService
) : Action() {

    override var nextAction: String? = "tobuyComplateAction"

    @Transactional
    override fun execute(@NotNull userId: String, @NotNull message: String): List<Message> {
        val (id, goods) = message.split(" ".toRegex(), 2)
                .let { it[0].toInt() to it[1] }

        val tobuy = tobuyService.findByIdAndGoods(id, userId, goods)
                ?: return listOf(
                        TextMessage("指定した商品は買い物リストにありませんでした"),
                        TextMessage("購入した商品を選択してください")
                )
        tobuyService.updateCompleted(tobuy)
        return listOf(
                TextMessage("${goods}を購入しました"),
                TextMessage("購入した商品を選択してください")
        )
    }

    override fun check(message: String): Boolean {
        return message.matches("[1-9][0-9]* .+".toRegex())
    }
}
