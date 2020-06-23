package com.linebot.action

import com.linebot.message.FlexMessageBuilder
import com.linebot.model.UserStatus
import com.linebot.service.cache.UserStatusCacheService
import com.linebot.service.notice.NoticeService
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
    private val noticeService: NoticeService,
    private val flexMessageBuilder: FlexMessageBuilder
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
                    it.execute(userId, message).also { _ ->
                        userStatusCacheService.set(userId, UserStatus(userId, it.nextAction))
                    }
                } ?: return handleError(userId)
    }

    private fun executeStartAction(actionSelector: ActionSelector, userId: String, message: String): List<Message> {
        val action = getStartAction(actionSelector)
        // スタートワードなのでチェック不要でexecute実行する
        val result = action.execute(userId, message)
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
