package com.linebot.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "bot_user")
data class BotUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "user_id")
        var userId: String? = null,

        @Column(name = "deleted")
        var deleted: String? = null,

        @Column(name = "created_date")
        var createdDate: Date? = null
)