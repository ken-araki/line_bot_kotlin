package com.linebot.action.tobuy

import com.linebot.action.Action
import com.linebot.service.tobuy.TobuyService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TobuyAddAction(
    private val tobuyService: TobuyService
) : Action() {
    override var nextAction: String? = "tobuyAddAction"
    @Transactional
    override fun execute(userId: String, message: String): List<Message> {
        val resultInsert = message.split(Utils.LINE_SEPARATOR.toRegex())
            .filter { it.isNotEmpty() }
            .map { tobuyService.insertByGoods(userId, it) }
            .sum()
        val messageInsert = """
            ${resultInsert}件の買い物リストを登録しました.
            他にあればそのまま記載してください
        """.trimIndent()
        return listOf(TextMessage(messageInsert))
    }
}
