package com.linebot.service.notice

import com.linebot.service.message.PushMessageService
import com.linebot.service.user.BotUserService
import com.linecorp.bot.model.message.TextMessage
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import org.springframework.stereotype.Service

@Service
class NoticeGarbageOutService(
    private val pushMessageService: PushMessageService,
    private val botUserService: BotUserService
) {

    /**
     * ゴミ出し前日であるかチェックする
     * Spring-Scheduleで木曜日チェック済みだが、別ルートで叩くことも考慮しここでもチェックする
     */
    // あしたが、第1, 第3金曜日の場合はTrueを返す
    fun isTommorowFirstOrThirdFriday(): Boolean {
        return checkDay(1) { day, dayOfWeek ->
            DayOfWeek.FRIDAY == dayOfWeek && (day in 1..7 || day in 15..21)
        }
    }

    fun executeDayBefore() {
        if (isTommorowFirstOrThirdFriday()) {
            val userIds = botUserService.findActiveUser()
                    .filterNot { it.userId.isNullOrEmpty() }
                    .mapNotNull { it.userId }
                    .toSet()

            if (userIds.isNotEmpty()) {
                pushMessageService.multicast(userIds, listOf(TextMessage("明日は資源ごみの日です。")))
            }
        }
    }

    fun checkDay(add: Int, fn: (Int, DayOfWeek) -> Boolean): Boolean {
        val target = LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusDays(add.toLong())
        val dayOfWeek = target.dayOfWeek
        val day = target.dayOfMonth
        return fn(day, dayOfWeek)
    }
}
