package com.linebot.controller.test

import com.linebot.action.ActionHandler
import com.linebot.util.Utils
import com.linecorp.bot.model.message.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/test/action"])
@Profile("local")
class TobuyActionController(
        private val action: ActionHandler
) {
    val log: Logger = LoggerFactory.getLogger(TobuyActionController::class.java)

    @GetMapping(path = ["/tobuy/addstart"])
    fun addstart(): List<Message> {
        return action.handle("1", "買い物リスト追加")
    }

    @GetMapping(path = ["/tobuy/add"])
    fun add(
            @RequestParam(name = "goods", required = false, defaultValue = "test") goods: String
    ): List<Message> {
        return action.handle("1", goods)
    }

    @GetMapping(path = ["/tobuy/add2"])
    fun add2(
            @RequestParam(name = "goods1", required = false, defaultValue = "test1") goods1: String,
            @RequestParam(name = "goods2", required = false, defaultValue = "test2") goods2: String
    ): List<Message> {
        val message = goods1 + Utils.LINE_SEPARATOR + goods2
        return action.handle("1", message)
    }

    @GetMapping(path = ["/tobuy/confirm"])
    fun confirm(): List<Message> {
        return action.handle("1", "買い物リスト確認")
    }

    @GetMapping(path = ["/tobuy/complate"])
    fun complate(): List<Message> {
        return action.handle("1", "買い物リスト購入")
    }

    @GetMapping(path = ["/tobuy/complate2"])
    fun complate2(
            @RequestParam(name = "id") id: String,
            @RequestParam(name = "goods") goods: String
    ): List<Message> {
        return action.handle("1", "$id $goods")
    }
}
