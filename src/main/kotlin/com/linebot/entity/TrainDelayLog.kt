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
)