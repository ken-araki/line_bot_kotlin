package com.linebot.action

enum class ActionSelector(
        val startWord: String,
        val actionList: List<String>

) {
    TRAIN_DELAY("電車遅延設定", listOf("trainDelayInputAction", "TrainDelayAction")),
    TOBUY_ADD("買い物リスト追加", listOf("tobuyAddInputAction", "tobuyAddAction")),
    TOBUY_CONFIRM("買い物リスト確認", listOf("tobuyConfirmAction")),
    TOBUY_COMPLATE("買い物リスト購入", listOf("tobuyComplateInputAction", "tobuyAddAction")),
    QIITA_ITEM_LIST("qiita記事一覧取得", listOf("qiitaIdInputAction", "qiitaItemAction"));
    companion object {
        fun getByStartWord(message: String): ActionSelector? = values().firstOrNull { it.startWord == message }
    }

}
