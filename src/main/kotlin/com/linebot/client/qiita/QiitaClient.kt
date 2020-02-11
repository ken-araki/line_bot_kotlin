package com.linebot.client.qiita

import com.linebot.client.UriBuilder
import com.linebot.client.WebClient
import com.linebot.model.common.RequestParameter
import com.linebot.model.qiita.Item
import com.linebot.model.qiita.Stocker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class QiitaClient(
        private val webClient: WebClient
) {
    val log: Logger = LoggerFactory.getLogger(QiitaClient::class.java)

    fun getUserItem(userId: String, p: RequestParameter): List<Item> {
        val url = UriBuilder.QIITA_USER_ITEMS.build(p, userId)
        return listOf(*webClient[url, Array<Item>::class.java] ?: emptyArray())
    }

    fun getItemStocker(itemId: String, p: RequestParameter): List<Stocker> {
        val url = UriBuilder.QIITA_ITEM_STOCK.build(p, itemId)
        return listOf(*webClient[url, Array<Stocker>::class.java] ?: emptyArray())
    }
}
