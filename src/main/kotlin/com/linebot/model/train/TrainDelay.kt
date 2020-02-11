package com.linebot.model.train

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import lombok.extern.slf4j.Slf4j

/**
 * ほんとは"TrainDelayResponse"にしたかった。
 */
data class TrainDelay(
        var name: String? = null,
        var company: String? = null,
        var lastupdate_gmt: String? = null,
        var source: String? = null
)
