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
)
