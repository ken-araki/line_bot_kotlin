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
)
