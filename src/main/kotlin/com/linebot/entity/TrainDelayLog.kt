package com.linebot.entity

import lombok.Data
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name = "train_delay_log")
data class TrainDelayLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "train_name")
        var trainName: String? = null,

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
        fun builder(): TrainDelayLogBuiler = TrainDelayLogBuiler()
    }

    class TrainDelayLogBuiler {
        private var trainName: String? = null
        private var botActionName: String? = null
        private var message: String? = null
        private var yearMonth: String? = null
        private var day: Int = 0
        private var hour: Int = 0
        private var minute: Int = 0
        private var dayOfWeek: Int = 0
        private var execTime: Date? = null

        fun build(): TrainDelayLog {
            return TrainDelayLog(
                    trainName = trainName,
                    yearMonth = yearMonth,
                    day = day,
                    hour = hour,
                    minute = minute,
                    dayOfWeek = dayOfWeek,
                    execTime = execTime
            )
        }

        fun trainName(trainName: String?) = apply { this.trainName = trainName }
        fun yearMonth(yearMonth: String?) = apply { this.yearMonth = yearMonth }
        fun day(day: Int) = apply { this.day = day }
        fun hour(hour: Int) = apply { this.hour = hour }
        fun minute(minute: Int) = apply { this.minute = minute }
        fun dayOfWeek(dayOfWeek: Int) = apply { this.dayOfWeek = dayOfWeek }
        fun execTime(execTime: Date?) = apply { this.execTime = execTime }
    }
}
