package com.linebot.model.train

/**
 * ほんとは"TrainDelayResponse"にしたかった。
 */
data class TrainDelay(
    var name: String? = null,
    var company: String? = null,
    var lastupdate_gmt: String? = null,
    var source: String? = null
)
