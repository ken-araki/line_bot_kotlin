package com.linebot.message

import com.linebot.model.qiita.ItemSummary
import com.linecorp.bot.model.action.URIAction
import com.linecorp.bot.model.message.FlexMessage
import com.linecorp.bot.model.message.flex.component.Box
import com.linecorp.bot.model.message.flex.component.FlexComponent
import com.linecorp.bot.model.message.flex.component.Separator
import com.linecorp.bot.model.message.flex.component.Text
import com.linecorp.bot.model.message.flex.container.Bubble
import com.linecorp.bot.model.message.flex.container.Carousel
import com.linecorp.bot.model.message.flex.unit.FlexAlign
import com.linecorp.bot.model.message.flex.unit.FlexFontSize
import com.linecorp.bot.model.message.flex.unit.FlexLayout
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull

@Component
class QiitaItemMessage {

    /**
     * Qiitaの記事を3つずつCarousel化する
     * Carouselの最大数は10なので、表示可能な記事は30件となる
     * （sortは事前に行っておくこと）
     *
     * @param qiitaId qiitaユーザ名
     * @param list qiitaユーザに紐づく記事一覧
     * @return 配信メッセージ
     */
    fun build(qiitaId: String, list: List<ItemSummary>) = FlexMessage(
            "${qiitaId}さんの記事一覧",
            Carousel(buildBubbles(qiitaId, list.chunked(3)))
    )

    private fun buildBubbles(qiitaId: String, lists: List<List<ItemSummary>>): List<Bubble> {
        return lists.take(10)
                .map {
                    Bubble.builder()
                            .body(buildBody(qiitaId, it))
                            .build()
                }
    }

    private fun buildBody(qiitaId: String, list: List<ItemSummary>): Box {
        val body: MutableList<FlexComponent> = mutableListOf(buildItem(qiitaId, list.first()))
        // 2つ目以降はSeparatorを用意する
        list.drop(1)
                .forEach {
                    body += mutableListOf(
                            Separator.builder()
                                    .margin(FlexMarginSize.XXL)
                                    .build(),
                            buildItem(qiitaId, it)
                    )
                }
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(body)
                .build()
    }

    private fun buildItem(qiitaId: String, item: ItemSummary): Box {
        val action = URIAction(
                "item link",
                "https://qiita.com/${qiitaId}/items/${item.id}",
                null
        )
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(
                        listOf(
                                buildTitle(item.title!!),
                                buildCounter(item)
                        )
                )
                .action(action)
                .build()
    }

    private fun buildTitle(title: String): Text {
        return Text.builder()
                .size(FlexFontSize.LG)
                .text(title)
                .wrap(true)
                .weight(Text.TextWeight.BOLD)
                .margin(FlexMarginSize.MD)
                .build()
    }

    private fun buildCounter(item: ItemSummary): Box {
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(
                        listOf(
                                buildCounterRecord("いいね", item.likesCount),
                                buildCounterRecord("ストック", item.stockersCount)
                        )
                )
                .build()
    }

    private fun buildCounterRecord(word: String, count: Int): Box {
        return Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .contents(
                        listOf(
                                buildCounterTitle(word),
                                buildCounterValue(count)
                        )
                )
                .build()
    }

    private fun buildCounterTitle(word: String): Text {
        return Text.builder()
                .flex(0)
                .text(word)
                .size(FlexFontSize.SM)
                .color("#555555")
                .build()
    }

    private fun buildCounterValue(count: Int): Text {
        return Text.builder()
                .text(count.toString())
                .size(FlexFontSize.SM)
                .color("#111111")
                .align(FlexAlign.END)
                .build()
    }
}

