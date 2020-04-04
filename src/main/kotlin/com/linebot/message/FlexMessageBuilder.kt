package com.linebot.message

import com.linebot.action.ActionSelector
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
class FlexMessageBuilder {
    fun buildStartWordMessage(): FlexMessage {
        val startWordAction: List<MessageAction> = ActionSelector.values()
                .map {
                    val startWords = it.startWord
                    MessageAction(startWords, startWords)
                }

        return FlexMessage(
                "どの操作を実行するか選択してください",
                buildBubble(startWordAction)
        )
    }

    fun buildListMessageAction(alt: String, actions: List<MessageAction>): FlexMessage {
        val bubbles = actions.chunked(5)
                .map { buildBubble(it) }
        return FlexMessage(alt, Carousel(bubbles))
    }

    private fun buildBubble(actions: List<MessageAction>): Bubble {
        return Bubble.builder()
                .body(buildBox(actions))
                .build()
    }

    private fun buildBox(actions: List<MessageAction>): Box {
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .flex(0)
                .spacing(FlexMarginSize.SM)
                .contents(actions.map { buildButton(it) })
                .build()
    }

    private fun buildButton(action: MessageAction): Button {
        return Button.builder()
                .style(Button.ButtonStyle.LINK)
                .height(Button.ButtonHeight.SMALL)
                .margin(FlexMarginSize.MD)
                .action(action)
                .build()
    }
}
