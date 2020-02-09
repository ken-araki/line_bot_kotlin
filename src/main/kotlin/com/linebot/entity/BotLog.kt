package com.linebot.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "bot_log")
data class BotLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "user_id")
        var userId: String? = null,

        @Column(name = "bot_action_name")
        var botActionName: String? = null,

        @Column(name = "message")
        var message: String? = null,

        @Column(name = "year_month")
        var yearMonth: String? = null,

        @Column(name = "day")
        var day: Int = 0,

        @Column(name = "day_of_week")
        var dayOfWeek: Int = 0,

        @Column(name = "hour")
        var hour: Int = 0,

        @Column(name = "minute")
        var minute: Int = 0,

        @Column(name = "exec_time")
        var execTime: Date? = null
) {
    companion object {
        @JvmStatic
        fun builder(): BotLogBuilder = BotLogBuilder()
    }

    class BotLogBuilder {
        private var userId: String? = null
        private var botActionName: String? = null
        private var message: String? = null
        private var yearMonth: String? = null
        private var day: Int = 0
        private var hour: Int = 0
        private var minute: Int = 0
        private var dayOfWeek: Int = 0
        private var execTime: Date? = null

        fun build(): BotLog {
            return BotLog(
                    userId = userId,
                    botActionName = botActionName,
                    message = message,
                    yearMonth = yearMonth,
                    day = day,
                    hour = hour,
                    minute = minute,
                    dayOfWeek = dayOfWeek,
                    execTime = execTime
            )
        }

        fun userId(userId: String?) = apply { this.userId = userId }
        fun botActionName(botActionName: String?) = apply { this.botActionName = botActionName }
        fun message(message: String?) = apply { this.message = message }
        fun yearMonth(yearMonth: String?) = apply { this.yearMonth = yearMonth }
        fun day(day: Int) = apply { this.day = day }
        fun hour(hour: Int) = apply { this.hour = hour }
        fun minute(minute: Int) = apply { this.minute = minute }
        fun dayOfWeek(dayOfWeek: Int) = apply { this.dayOfWeek = dayOfWeek }
        fun execTime(execTime: Date?) = apply { this.execTime = execTime }
    }
}
