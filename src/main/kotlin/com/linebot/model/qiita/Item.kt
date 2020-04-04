package com.linebot.model.qiita

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class Item(
    // https://qiita.com/api/v2/docs#get-apiv2usersuser_iditems
    // いろんなデータがあるが、いったん欲しい情報だけ抽出する
    // 記載順序も変えている
    var id: String? = null,
    var title: String? = null,
    var url: String? = null,
    var coediting: Boolean = false,
    @JsonProperty("comments_count")
    var commentsCount: Int = 0,
    @JsonProperty("likes_count")
    var likesCount: Int = 0,
    @JsonProperty("page_views_count")
    var pageViewsCount: Int = 0,
    @JsonProperty("reactions_count")
    var reactionsCount: Int = 0,
    @JsonProperty("created_at")
    var createdAt: Date? = null,
    @JsonProperty("updated_at")
    var updatedAt: Date? = null
)
