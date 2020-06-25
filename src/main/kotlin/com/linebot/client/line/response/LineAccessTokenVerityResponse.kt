package com.linebot.client.line.response

import com.fasterxml.jackson.annotation.JsonProperty

// https://developers.line.biz/ja/reference/social-api/#レスポンス-2
data class LineAccessTokenVerityResponse(
    @JsonProperty("scope")
    val scope: String,
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("expires_in")
    val expiresIn: Long
)
