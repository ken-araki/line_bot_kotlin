package com.linebot.security.filter

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class AppPreAuthenticatedFilter: AbstractPreAuthenticatedProcessingFilter() {
    companion object {
        const val TOKEN_HEADER = "X_ARK_TOKEN"
        const val AUTHENTICATION_HEADER = "X_ARK_AUTHENTICATION"
    }
    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): String {
        return request.getHeader(TOKEN_HEADER)
    }

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): String {
        return request.getHeader(AUTHENTICATION_HEADER)
    }
}