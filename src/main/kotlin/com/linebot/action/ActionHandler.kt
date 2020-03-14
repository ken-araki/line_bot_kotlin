package com.linebot.action

import com.linebot.message.FlexMessageBuilder
import com.linebot.model.UserStatus
import com.linebot.service.UserStatusCacheService
import com.linebot.service.log.LogService
import com.linebot.service.notice.NoticeService
import com.linebot.service.user.BotUserQiitaService
import com.linebot.service.user.BotUserService
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ActionHandler(
        private val applicationContext: ApplicationContext,
        private val userStatusCacheService: UserStatusCacheService,
        private val botUserService: BotUserService,
        private val botUserQiitaService: BotUserQiitaService,
        private val noticeService: NoticeService,
        private val flexMessageBuilder: FlexMessageBuilder,
        private val logService: LogService
) {

    fun follow(userId: String): List<Message> {
        botUserService.insert(userId)
        return listOf(
                TextMessage("ユーザ登録を行いました。以下操作が実行可能です。"),
                flexMessageBuilder.buildStartWordMessage()
        )
    }

    @Transactional
    fun unfollow(userId: String) {
        botUserService.delete(userId)
        botUserQiitaService.delete(userId)
        noticeService.deleteNotice(userId)
    }

    fun handle(userId: String, message: String): List<Message> {
        val actionSelector = ActionSelector.getByStartWord(message)
        if (actionSelector != null) {
            return executeStartAction(actionSelector, userId, message)
        }

        return getStatus(userId).nextAction
                ?.let { getAction(it) }
                ?.takeIf { it.check(message) }
                ?.let {
                    execute(it, userId, message).also { _ ->
                        userStatusCacheService.set(userId, UserStatus(userId, it.nextAction))
                    }
                } ?: return handleError(userId)
    }

    private fun execute(action: Action, userId: String, message: String): List<Message> {
        logService.insertBotLog(userId, action.javaClass.simpleName, message)
        return action.execute(userId, message)
    }

    private fun executeStartAction(actionSelector: ActionSelector, userId: String, message: String): List<Message> {
        val action = getStartAction(actionSelector)
        // スタートワードなのでチェック不要でexecute実行する
        val result = execute(action, userId, message)
        userStatusCacheService.set(userId, UserStatus(userId, action.nextAction))
        return result
    }

    private fun handleError(userId: String): List<Message> {
        userStatusCacheService.delete(userId)
        return listOf(
                TextMessage("このメッセージは受け付けられません。どの操作を実行するか選択してください"),
                flexMessageBuilder.buildStartWordMessage()
        )
    }

    private fun getStartAction(actionSelector: ActionSelector) = getAction(actionSelector.actionList.first())
    private fun getAction(actionName: String) = applicationContext.getBean(actionName) as Action
    private fun getStatus(userId: String) = userStatusCacheService.get(userId) ?: UserStatus()
}
