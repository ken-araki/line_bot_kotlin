package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linebot.service.tobuy.TobuyService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TobuyComplateAction(
    private val tobuyService: TobuyService
) : Action() {
    override var nextAction: String? = "tobuyComplateAction"

    @Transactional
    override fun execute(userId: String, message: String): List<Message> {
        val (id, goods) = message.split(" ".toRegex(), 2)
            .let { it[0].toInt() to it[1] }
        val noItemMessage = """
            指定した商品は買い物リストにありませんでした.
            購入した商品を選択してください
        """.trimIndent()

        tobuyService.findByIdAndGoods(id, userId, goods)
            ?.also { tobuyService.updateCompleted(it) }
            ?: return listOf(TextMessage(noItemMessage))
        val goodsMessage = """
            ${goods}を購入しました.
            購入した商品を選択してください
        """.trimIndent()
        return listOf(TextMessage(goodsMessage))
    }

    override fun check(message: String): Boolean {
        return message.matches("[1-9][0-9]* .+".toRegex())
    }
}
