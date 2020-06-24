package com.linebot.client.line.response

import com.fasterxml.jackson.annotation.JsonProperty

// https://developers.line.biz/ja/reference/social-api/#レスポンス-5
data class LineVerityResponse(
    @JsonProperty("iss")
    val iss: String,
    @JsonProperty("sub")
    val sub: String,
    @JsonProperty("aud")
    val aud: String,
    @JsonProperty("exp")
    val exp: Long,
    @JsonProperty("iat")
    val iat: Long,
    @JsonProperty("auth_time")
    val authTime: Long,
    @JsonProperty("nonce")
    val nonce: String,
    @JsonProperty("amr")
    val amr: List<String> = emptyList(),
    @JsonProperty("name")
    val name: String,
    @JsonProperty("picture")
    val picture: String
)
