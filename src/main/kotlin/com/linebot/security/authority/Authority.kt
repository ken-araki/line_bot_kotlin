package com.linebot.security.authority

import org.springframework.security.core.GrantedAuthority

class Authority: GrantedAuthority {
    override fun getAuthority() = "ROLE_USER"
}