package com.linebot.service.tobuy

import com.linebot.entity.Tobuy
import com.linebot.repository.TobuyRepository
import com.linebot.util.Utils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TobuyService(
    private val tobuyRepository: TobuyRepository
) {

    @Transactional(readOnly = true)
    fun findByIsCompleted(userId: String, isCompleted: String): List<Tobuy> {
        return tobuyRepository.findByUserIdAndIsCompleted(userId, isCompleted)
    }

    @Transactional(readOnly = true)
    fun findByIdAndGoods(id: Int?, userId: String, goods: String): Tobuy? {
        return tobuyRepository.findByIdAndUserIdAndGoodsAndIsCompleted(id, userId, goods, "0")
    }

    @Transactional
    fun insertByGoods(userId: String, goods: String): Int {
        tobuyRepository.save(Tobuy(
                goods = goods,
                isCompleted = "0",
                createdDate = Utils.now(),
                updatedDate = Utils.now(),
                userId = userId
        ))
        return 1
    }

    @Transactional
    fun updateCompleted(tobuy: Tobuy): Int {
        tobuy.isCompleted = "1"
        tobuy.updatedDate = Utils.now()
        tobuyRepository.save(tobuy)
        return 1
    }

    @Transactional
    fun updateCompleted(tobuys: List<Tobuy>): Int {
        return tobuys.map {
            it.isCompleted = "1"
            it.updatedDate = Utils.now()
            tobuyRepository.save(it)
        }.count()
    }
}
