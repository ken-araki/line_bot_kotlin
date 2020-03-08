package com.linebot.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notice")
data class Notice(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "user_id")
        var userId: String? = null,

        @Column(name = "year")
        var year: Int = 0,

        @Column(name = "month")
        var month: Int = 0,

        @Column(name = "day")
        var day: Int = 0,

        @Column(name = "hour")
        var hour: Int = 0,

        @Column(name = "minute")
        var minute: Int = 0,

        @Column(name = "day_of_week")
        var dayOfWeek: Int = 0,

        @Column(name = "type")
        var type: Int = 0,

        @Column(name = "deleted")
        var deleted: String? = null,

        @Column(name = "created_at")
        var createdAt: Date? = null
) {
    enum class Type(
            val code: Int
    ) {
        TRAIN_DELAY(1),
        CRON(2)
    }
}
