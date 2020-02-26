package com.linebot.repository

import com.linebot.entity.Tobuy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TobuyRepository : JpaRepository<Tobuy, Long> {
    fun findByUserIdAndIsCompleted(userId: String, isCompleted: String): List<Tobuy>
    fun findByIdAndUserIdAndGoodsAndIsCompleted(id: Int?, userId: String, goods: String, isCompleted: String): Tobuy

    @Modifying
    @Query(value = "update tobuy set is_completed = '1', updated_date = current_timestamp where user_id = :userId and is_completed = '0';", nativeQuery = true)
    fun updateAllCompleted(@Param("userId") userId: String): Int
}
