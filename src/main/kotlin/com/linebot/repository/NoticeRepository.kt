package com.linebot.repository

import com.linebot.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : JpaRepository<Notice, Long> {
    fun findByTypeAndDeleted(type: Int, deleted: String): List<Notice>
    fun findByUserIdAndDeleted(userId: String, deleted: String): List<Notice>
    fun findByUserIdAndType(userId: String, type: Int): List<Notice>
}
