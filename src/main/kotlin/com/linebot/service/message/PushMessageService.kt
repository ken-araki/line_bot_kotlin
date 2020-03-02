package com.linebot.service.message

import com.linebot.client.self.SelfClient
import com.linebot.util.Utils
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.Broadcast
import com.linecorp.bot.model.Multicast
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.response.BotApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PushMessageService(
        private val lineMessagingClient: LineMessagingClient
) {

    val log: Logger = LoggerFactory.getLogger(PushMessageService::class.java)

    fun broadcast(messages: List<Message>): BotApiResponse {
        log.info("send broadcast. messages: {}", messages)
        return Utils.uncheck<BotApiResponse> {
            val response = lineMessagingClient.broadcast(Broadcast(messages, false)).get()
            log.info("broadcast result. {}", response)
            response
        }
    }

    fun multicast(userIds: Set<String>, messages: List<Message>): BotApiResponse {
        log.info("send multicast. userIds: {}, messages: {}", userIds, messages)
        return Utils.uncheck<BotApiResponse> {
            val response = lineMessagingClient.multicast(Multicast(userIds, messages)).get()
            log.info("multicast result. {}", response)
            response
        }
    }

    fun pushMessage(userId: String, messages: List<Message>): BotApiResponse {
        log.info("send pushMessage. userId: {}, messages: {}", userId, messages)
        return Utils.uncheck<BotApiResponse> {
            val response = lineMessagingClient.pushMessage(PushMessage(userId, messages)).get()
            log.info("pushMessage result. {}", response)
            response
        }
    }
}
