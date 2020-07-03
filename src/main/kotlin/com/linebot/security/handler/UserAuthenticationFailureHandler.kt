package com.linebot.security.handler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class UserAuthenticationFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
    }
}
