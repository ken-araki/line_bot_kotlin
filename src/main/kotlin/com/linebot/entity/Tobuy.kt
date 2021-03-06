package com.linebot.entity

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tobuy")
data class Tobuy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null,
    @Column(name = "goods")
    var goods: String? = null,
    @Column(name = "is_completed")
    var isCompleted: String? = null,
    @Column(name = "created_date")
    var createdDate: Date? = null,
    @Column(name = "updated_date")
    var updatedDate: Date? = null,
    // postgres は列追加の場所を指定できない
    @Column(name = "user_id")
    var userId: String? = null
) {
    fun setIsCompleted(isCompleted: String?) = apply { this.isCompleted = isCompleted }
}
