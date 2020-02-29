package com.linebot.action.qiita

import com.linebot.action.Action
import com.linebot.entity.BotUserQiita
import com.linebot.service.user.BotUserQiitaService
import com.linebot.util.Utils
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.validation.constraints.NotNull
import java.util.Collections

@Component
class QiitaIdInputAction(
        private val botUserQiitaService: BotUserQiitaService
) : Action() {

    override var nextAction: String? = "qiitaIdAction"

    override fun execute(userId: String, message: String): List<Message> {
        val replyMessage = botUserQiitaService.findByUserId(userId)?.qiitaUserId
                ?.let {
                    StringBuilder("${it}が設定されています。")
                            .append(Utils.LINE_SEPARATOR)
                            .append("変更する場合は、QiitaのUser名を入力してください")
                            .toString()
                }
                ?: "QiitaのUser名を入力してください"
        return listOf(TextMessage(replyMessage))
    }
}
