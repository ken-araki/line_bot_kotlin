package com.linebot.security.user

import com.linebot.config.PropertiesConfig
import com.linebot.service.line.LineService
import com.linebot.service.user.BotUserService
import javax.security.auth.message.AuthException
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component

@Component
class AppUserDetailsService(
    private val lineService: LineService,
    private val botUserService: BotUserService,
    private val propertiesConfig: PropertiesConfig
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken): UserDetails {
        // サーバ、フロント間で取り決めたtokenが必須
        val authToken = token.credentials as String
        if (propertiesConfig.token != authToken) {
            throw AuthException("invalid auth token. header: $authToken.")
        }
        // jwt token を利用した認証を行う
        val jwtToken = token.principal as String
        val (appUserId, accessToken) = lineService.decodeAuthJwt(jwtToken)
        val user = botUserService.findActiveUserByAppUserId(appUserId) ?: throw AuthException("invalid jwt token.")
        return AppUserDetail()
            .apply {
                this.user = AppUser(
                    user = user,
                    appUserId = appUserId,
                    accessToken = accessToken
                )
            }
    }
}
