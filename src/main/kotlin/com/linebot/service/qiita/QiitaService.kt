package com.linebot.service.qiita

import com.linebot.client.qiita.QiitaClient
import com.linebot.model.common.RequestParameter
import com.linebot.model.qiita.ItemSummary
import org.springframework.stereotype.Service

@Service
class QiitaService(
        private val qiitaClient: QiitaClient
) {

    fun getItemByUserId(userId: String, p: RequestParameter): List<ItemSummary> {
        return qiitaClient.getUserItem(userId, p)
                .map {
                    val stock = qiitaClient.getItemStocker(it.id!!, p).size
                    ItemSummary(
                            id = it.id,
                            title = it.title,
                            url = it.url,
                            likesCount = it.likesCount,
                            stockersCount = stock,
                            pageViewsCount = it.pageViewsCount
                    )
                }
                .sortedByDescending { it.stockersCount }
                .sortedByDescending { it.likesCount }
    }
}
