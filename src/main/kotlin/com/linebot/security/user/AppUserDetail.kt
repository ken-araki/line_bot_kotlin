package com.linebot.security.user

import com.linebot.security.authority.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AppUserDetail : UserDetails {
    lateinit var user: AppUser

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(Authority())
    }

    override fun isEnabled() = true

    override fun getUsername(): String = ""

    override fun getPassword(): String = ""

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}
