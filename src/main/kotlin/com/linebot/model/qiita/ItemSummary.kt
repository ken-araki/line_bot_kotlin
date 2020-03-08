package com.linebot.model.qiita

data class ItemSummary(
        var id: String? = null,
        var title: String? = null,
        var url: String? = null,
        var likesCount: Int = 0,
        var pageViewsCount: Int = 0,
        var stockersCount: Int = 0
)
