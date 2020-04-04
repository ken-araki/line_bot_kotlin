package com.linebot.client.train

import com.linebot.client.UriBuilder
import com.linebot.client.WebClient
import com.linebot.model.train.TrainDelay
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TrainDelayClient(
    private val webClient: WebClient
) {

    val log: Logger = LoggerFactory.getLogger(TrainDelayClient::class.java)

    /**
     * 鉄道comのRSSを集計して遅延している電車を返すAPIを実行する。
     * 電車遅延情報を取得する。
     * 参照URL: https://rti-giken.jp/fhc/api/train_tetsudo/
     *
     * @return 電車遅延情報リスト
     */
    val delay: List<TrainDelay> = listOf(*webClient[UriBuilder.TRAIN_DELAY.build(), Array<TrainDelay>::class.java]
            ?: emptyArray())
}
