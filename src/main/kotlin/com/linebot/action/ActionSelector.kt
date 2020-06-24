package com.linebot.action

enum class ActionSelector(
    val startWord: String,
    val actionList: List<String>

) {
    TOBUY_ADD("買い物リスト追加", listOf("tobuyAddInputAction", "tobuyAddAction")),
    TOBUY_CONFIRM("買い物リスト確認", listOf("tobuyConfirmAction")),
    TOBUY_COMPLATE("買い物リスト購入", listOf("tobuyComplateInputAction", "tobuyAddAction"));
    companion object {
        fun getByStartWord(message: String): ActionSelector? = values().firstOrNull { it.startWord == message }
    }
}
