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

    companion object {
        @JvmStatic
        fun builder(): NoticeBuilder = NoticeBuilder()
    }

    enum class Type(
            val code: Int
    ) {
        TRAIN_DELAY(1),
        CRON(2)
    }

    class NoticeBuilder {
        private var userId: String? = null
        private var year: Int = 0
        private var month: Int = 0
        private var day: Int = 0
        private var hour: Int = 0
        private var minute: Int = 0
        private var dayOfWeek: Int = 0
        private var type: Int = 0
        private var deleted: String? = null
        private var createdAt: Date? = null

        fun build(): Notice {
            return Notice(
                    userId = userId,
                    year = year,
                    month = month,
                    day = day,
                    hour = hour,
                    minute = minute,
                    dayOfWeek = dayOfWeek,
                    type = type,
                    deleted = deleted,
                    createdAt = createdAt
            )
        }

        fun userId(userId: String?) = apply { this.userId = userId }
        fun year(year: Int) = apply { this.year = year }
        fun month(month: Int) = apply { this.month = month }
        fun day(day: Int) = apply { this.day = day }
        fun hour(hour: Int) = apply { this.hour = hour }
        fun minute(minute: Int) = apply { this.minute = minute }
        fun dayOfWeek(dayOfWeek: Int) = apply { this.dayOfWeek = dayOfWeek }
        fun type(type: Int) = apply { this.type = type }
        fun deleted(deleted: String?) = apply { this.deleted = deleted }
        fun createdAt(createdAt: Date?) = apply { this.createdAt = createdAt }
    }

}
