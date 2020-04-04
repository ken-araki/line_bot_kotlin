package com.linebot.client

import com.linebot.model.common.RequestParameter
import org.springframework.web.util.UriComponentsBuilder

enum class UriBuilder(
    val path: String
) {
    QIITA_USER_ITEMS("https://qiita.com/api/v2/users/{userId}/items"),
    QIITA_ITEM_STOCK("https://qiita.com/api/v2/items/{itemId}/stockers"),
    TRAIN_DELAY("https://tetsudo.rti-giken.jp/free/delay.json");

    fun build(p: RequestParameter, pathvalue: String): String {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .replaceQueryParams(p.convert())
                .buildAndExpand(pathvalue)
                .toUriString()
    }

    fun build(pathvalue: String): String {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .buildAndExpand(pathvalue)
                .toUriString()
    }

    fun build(p: RequestParameter): String {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .replaceQueryParams(p.convert())
                .toUriString()
    }

    fun build(): String {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .toUriString()
    }
}
