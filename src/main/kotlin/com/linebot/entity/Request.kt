package com.linebot.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "request")
data class Request(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,
        @Column(name = "user_id")
        var userId: String? = null,
        @Column(name = "request")
        var request: String? = null,
        @Column(name = "status")
        var status: Int = 0,
        @Column(name = "deleted")
        var deleted: String? = null,
        @Column(name = "created_at")
        var createdAt: Date? = null
) {
    companion object {
        @JvmStatic
        fun builder(): RequestBuiler = RequestBuiler()
    }

    class RequestBuiler {
        private var userId: String? = null
        private var request: String? = null
        private var status: Int? = null
        private var deleted: String? = null
        private var createdAt: Date? = null

        fun build(): Request {
            return Request(
                    userId = userId,
                    request = request,
                    status = status ?: 0,
                    deleted = deleted,
                    createdAt = createdAt
            )
        }

        fun userId(userId: String?) = apply { this.userId = userId }
        fun request(request: String?) = apply { this.request = request }
        fun status(status: Int?) = apply { this.status = status }
        fun deleted(deleted: String?) = apply { this.deleted = deleted }
        fun createdAt(createdAt: Date?) = apply { this.createdAt = createdAt }
    }
}
