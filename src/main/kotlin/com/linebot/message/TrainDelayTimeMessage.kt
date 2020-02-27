package com.linebot.message

import com.linebot.action.traindelay.TrainDelayTime
import com.linecorp.bot.model.action.MessageAction
import com.linecorp.bot.model.message.FlexMessage
import com.linecorp.bot.model.message.flex.component.Box
import com.linecorp.bot.model.message.flex.component.Button
import com.linecorp.bot.model.message.flex.container.Bubble
import com.linecorp.bot.model.message.flex.container.Carousel
import com.linecorp.bot.model.message.flex.unit.FlexLayout
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize
import org.springframework.stereotype.Component

@Component
class TrainDelayTimeMessage {
    private var bobbleRow = 10

    fun build(alt: String): FlexMessage {
        val lists = TrainDelayTime.values()
                .map { MessageAction(it.time, it.time) }
                .chunked(bobbleRow)
        return FlexMessage(alt, Carousel(buildBubbles(lists)))
    }

    private fun buildBubbles(lists: List<List<MessageAction>>): List<Bubble> {
        return lists.take(10)
                .map {
                    Bubble.builder()
                            .body(buildBody(it))
                            .build()
                }
    }

    private fun buildBody(list: List<MessageAction>): Box {
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .flex(0)
                .spacing(FlexMarginSize.SM)
                .contents(list.map { buildButton(it) })
                .build()
    }

    private fun buildButton(action: MessageAction): Button {
        return Button.builder()
                .style(Button.ButtonStyle.LINK)
                .height(Button.ButtonHeight.SMALL)
                .margin(FlexMarginSize.SM)
                .action(action)
                .build()
    }
}
