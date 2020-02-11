package com.linebot.model.qiita

data class ItemSummary(
        var id: String? = null,
        var title: String? = null,
        var url: String? = null,
        var likesCount: Int = 0,
        var pageViewsCount: Int = 0,
        var stockersCount: Int = 0
) {
    companion object {
        @JvmStatic
        fun builder(): ItemSummaryBuilder = ItemSummaryBuilder()
    }

    class ItemSummaryBuilder {
        var id: String? = null
        var title: String? = null
        var url: String? = null
        var likesCount: Int? = null
        var pageViewsCount: Int? = null
        var stockersCount: Int? = null

        fun build(): ItemSummary {
            return ItemSummary(
                    id = id,
                    title = title,
                    url = url,
                    likesCount = likesCount ?: 0,
                    pageViewsCount = pageViewsCount ?: 0,
                    stockersCount = stockersCount ?: 0
            )
        }

        fun id(id: String?) = apply { this.id = id }
        fun title(title: String?) = apply { this.title = title }
        fun url(url: String?) = apply { this.url = url }
        fun likesCount(likesCount: Int?) = apply { this.likesCount = likesCount }
        fun pageViewsCount(pageViewsCount: Int?) = apply { this.pageViewsCount = pageViewsCount }
        fun stockersCount(stockersCount: Int?) = apply { this.stockersCount = stockersCount }
    }
}
