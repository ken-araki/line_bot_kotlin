package com.linebot.controller.auth

import com.linebot.config.PropertiesConfig
import com.linebot.service.line.LineService
import javax.servlet.http.HttpSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(path = ["/line/auth"])
class AuthController(
    private val lineService: LineService,
    private val propertiesConfig: PropertiesConfig
) {
    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)
    @GetMapping
    fun auth(
        httpSession: HttpSession,
        @RequestParam("code") code: String,
        @RequestParam("state") state: String,
        @RequestParam("liffRedirectUri") liffRedirectUri: String
    ): String {
        httpSession.setAttribute("jwt", lineService.auth(code, liffRedirectUri))
        return "redirect:${propertiesConfig.redirectUri}"
    }
}
