package com.linebot.controller.test

import com.linebot.client.qiita.QiitaClient
import com.linebot.model.qiita.Item
import com.linebot.model.qiita.ItemSummary
import com.linebot.model.qiita.QiitaRequestParameter
import com.linebot.service.qiita.QiitaService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/test/qiita"])
@Profile("local")
class QiitaController(
        private val client: QiitaClient,
        private val qiitaService: QiitaService
) {
    val log: Logger = LoggerFactory.getLogger(QiitaController::class.java)

    @GetMapping("/itemSummary")
    fun getItemSummaries(): List<ItemSummary> {
        val p = QiitaRequestParameter(1, 100)
        return qiitaService.getItemByUserId("kaad", p)
    }

    @GetMapping("/items")
    fun getItems(): List<Item> {
        val p = QiitaRequestParameter(1, 100)
        return client.getUserItem("kaad", p)
    }
}
