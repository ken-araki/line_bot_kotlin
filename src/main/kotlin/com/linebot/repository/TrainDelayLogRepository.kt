package com.linebot.repository

import com.linebot.entity.TrainDelayLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrainDelayLogRepository : JpaRepository<TrainDelayLog, Long>