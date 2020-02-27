package com.linebot.batch

import com.linebot.client.self.SelfClient
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
@Profile("local", "production")
class KeepAliveBatch(
        private val selfClient: SelfClient
) {

    // LINE bot は30秒以内にレスポンスしないと、そっとなかったことにされてしまう（何も帰らずエラーになる）ので
    // Heroku上で常時稼働するように自身へHTTPリクエストを投げ続ける
    // ただし、Web + DB で1000hが無料枠であるため、7:00 - 24:00 を稼働時間とする（それ以外の時間は保証しない）
    @Scheduled(cron = "0 */20 7-23 * * *", zone = "Asia/Tokyo")
    fun execute() {
        // Controller側でlog出しているのでここでは何もしない
        selfClient.keepAlive()
    }
}
