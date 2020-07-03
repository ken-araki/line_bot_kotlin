package com.linebot.security

import com.linebot.config.PropertiesConfig
import com.linebot.security.endpoint.Http401UnauthorizedEndpoint
import com.linebot.security.filter.AppPreAuthenticatedFilter
import com.linebot.security.handler.UserAuthenticationFailureHandler
import com.linebot.security.handler.UserAuthenticationSuccessHandler
import com.linebot.security.user.AppUserDetailsService
import com.linebot.service.line.LineService
import com.linebot.service.user.BotUserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val lineService: LineService,
    private val botUserService: BotUserService,
    private val propertiesConfig: PropertiesConfig
) : WebSecurityConfigurerAdapter() {

    fun authenticationUserDetailsService(): AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> =
        AppUserDetailsService(lineService, botUserService, propertiesConfig)

    fun preAuthenticatedAuthenticationProvider(): PreAuthenticatedAuthenticationProvider =
        PreAuthenticatedAuthenticationProvider()
            .apply {
                setPreAuthenticatedUserDetailsService(authenticationUserDetailsService())
                setUserDetailsChecker(AccountStatusUserDetailsChecker())
            }

    // Filter に @Bean や @Component を付与するとignoreが効かなくなるので注意
    fun preAuthenticatedProcessingFilter(): AbstractPreAuthenticatedProcessingFilter =
        AppPreAuthenticatedFilter().apply {
            setAuthenticationManager(authenticationManager())
            setAuthenticationSuccessHandler(UserAuthenticationSuccessHandler())
            setAuthenticationFailureHandler(UserAuthenticationFailureHandler())
        }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider())
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/self/keep", "/retroactive/**", "/sample/line/token", "/line/token", "/callback")
    }

    override fun configure(http: HttpSecurity) {
        http.addFilter(preAuthenticatedProcessingFilter())
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v*/**").authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(Http401UnauthorizedEndpoint())
        // @TODO: cors対策を入れる
    }
}
