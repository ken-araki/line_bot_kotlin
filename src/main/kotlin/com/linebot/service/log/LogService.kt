package com.linebot.service.log

import com.linebot.entity.BotLog
import com.linebot.entity.TrainDelayLog
import com.linebot.model.train.TrainDelay
import com.linebot.repository.BotLogRepository
import com.linebot.repository.TrainDelayLogRepository
import com.linebot.util.Utils
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.stream.Collectors

@AllArgsConstructor
class LogService(
        private val botLogRepository: BotLogRepository,
        private val trainDelayLogRepository: TrainDelayLogRepository
) {

    @Transactional
    fun insertBotLog(
            userId: String,
            botName: String,
            message: String
    ): BotLog {
        val now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"))
        return botLogRepository.save(
                BotLog(
                        userId = userId,
                        botActionName = botName,
                        message = message,
                        yearMonth = "${now.year}${now.monthValue}",
                        day = now.dayOfMonth,
                        dayOfWeek = now.dayOfWeek.value,
                        hour = now.hour,
                        minute = now.minute,
                        execTime = Utils.now()
                )
        )
    }

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
