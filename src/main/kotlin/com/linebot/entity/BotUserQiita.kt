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
) {
    companion object {
        @JvmStatic
        fun builder(): BotUserQiitaBuilder = BotUserQiitaBuilder()
    }

    class BotUserQiitaBuilder {
        private var userId: String? = null
        private var qiitaUserId: String? = null
        private var deleted: String? = null
        private var createdDate: Date? = null

        fun build(): BotUserQiita = BotUserQiita(
                userId = userId,
                qiitaUserId = qiitaUserId,
                deleted = deleted ?: "0",
                createdDate = createdDate
        )

        fun userId(userId: String) = apply { this.userId = userId }
        fun qiitaUserId(qiitaUserId: String) = apply { this.qiitaUserId = qiitaUserId }
        fun deleted(deleted: String) = apply { this.deleted = deleted }
        fun createdDate(createdDate: Date) = apply { this.createdDate = createdDate }
    }
}
