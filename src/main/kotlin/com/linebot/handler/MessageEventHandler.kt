package com.linebot.handler

import com.linebot.action.ActionHandler
import com.linebot.client.WebClient
import com.linecorp.bot.model.event.FollowEvent
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.UnfollowEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
@LineMessageHandler
class MessageEventHandler(
    private val handler: ActionHandler
) {
    val log: Logger = LoggerFactory.getLogger(WebClient::class.java)

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>): List<Message> {
        val userId = event.source.userId
        val message = event.message.text

        log.info("callback. userId: {}, message: {}", userId, message)
        return handler.handle(userId, message)
    }

    @EventMapping
    fun handleFollowEvent(event: FollowEvent): List<Message> {
        val userId = event.source.userId

        log.info("follow. userId: {}", userId)
        return handler.follow(userId)
    }

    @EventMapping
    fun handleUnfollowEvent(event: UnfollowEvent) {
        val userId = event.source.userId

        log.info("unfollowed this bot: {}", userId)
        handler.unfollow(userId)
    }
}
