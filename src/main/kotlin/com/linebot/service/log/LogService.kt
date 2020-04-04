package com.linebot.service.log

import com.linebot.entity.TrainDelayLog
import com.linebot.model.train.TrainDelay
import com.linebot.repository.TrainDelayLogRepository
import com.linebot.util.Utils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime
import java.time.ZoneId

@Service
class LogService(
        private val trainDelayLogRepository: TrainDelayLogRepository
) {

    @Transactional
    fun insertTrainDelay(
            trains: List<TrainDelay>
    ): List<TrainDelayLog> {
        val now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"))
        val yearMonth = "${now.year}${now.monthValue}"
        val logs = trains.map {
            TrainDelayLog(
                    trainName = it.name,
                    yearMonth = yearMonth,
                    day = now.dayOfMonth,
                    dayOfWeek = now.dayOfWeek.value,
                    hour = now.hour,
                    minute = now.minute,
                    execTime = Utils.now()
            )
        }
        return trainDelayLogRepository.saveAll(logs)
    }

}
