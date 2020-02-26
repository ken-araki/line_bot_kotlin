package com.linebot.repository

import com.linebot.entity.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository : JpaRepository<Request, Long> {
    fun findByStatusAndDeleted(status: Int, deleted: String): List<Request>
}
