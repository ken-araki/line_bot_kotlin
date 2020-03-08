package com.linebot.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "bot_user_qiita")
data class BotUserQiita(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Int? = null,

        @Column(name = "user_id")
        var userId: String? = null,

        @Column(name = "qiita_user_id")
        var qiitaUserId: String? = null,

        @Column(name = "deleted")
        var deleted: String = "0",

        @Column(name = "created_date")
        var createdDate: Date? = null
)
