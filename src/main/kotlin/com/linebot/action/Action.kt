package com.linebot.action

import com.linecorp.bot.model.message.Message

abstract class Action {

    open var nextAction: String? = null
    open fun check(message: String) = true

    abstract fun execute(userId: String, message: String): List<Message>
}
