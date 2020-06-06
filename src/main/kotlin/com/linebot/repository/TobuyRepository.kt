package com.linebot.repository

import com.linebot.entity.Tobuy
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TobuyRepository : JpaRepository<Tobuy, Long> {
    fun findById(id: Int): Optional<Tobuy>
    fun findByUserIdAndIsCompleted(userId: String, isCompleted: String): List<Tobuy>
    fun findByIdAndUserIdAndGoodsAndIsCompleted(id: Int?, userId: String, goods: String, isCompleted: String): Tobuy?
}
