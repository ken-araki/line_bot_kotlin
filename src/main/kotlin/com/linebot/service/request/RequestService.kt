package com.linebot.service.request

import com.linebot.entity.Request
import com.linebot.repository.RequestRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RequestService(
        private val requestRepository: RequestRepository
) {

    @Transactional(readOnly = true)
    fun findActive(): List<Request> {
        return requestRepository.findByStatusAndDeleted(0, "0")
    }

    @Transactional
    fun insert(request: Request): Request {
        return requestRepository.save(request)
    }
}
