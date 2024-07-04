package com.devstudio.forexFusion.data.model

data class ForexSignal(
    val id: String = "0",
    val pairName: String,
    val imageUrl: String,
    val date: String,
    val entryPrice: String,
    val type: String, // short for sell / long for buy
    val takeProfit1: String,
    val takeProfit1Status: Int = 0, // 0 for not done yet and 1 for done
    val takeProfit2: String,
    val takeProfit2Status: Int = 0,
    val takeProfit3: String,
    val takeProfit3Status: Int = 0,
    val tp1Pips: String,
    val tp2Pips: String,
    val tp3Pips: String,
    val stopLose: String,
)
{
    constructor(): this(
        pairName = "",
        imageUrl = "",
        entryPrice = "",
        type = "",
        takeProfit1 = "",
        takeProfit2 = "",
        takeProfit3 = "",
        stopLose = "",
        date = "",
        tp1Pips = "",
        tp2Pips = "",
        tp3Pips = "",
    )
}
