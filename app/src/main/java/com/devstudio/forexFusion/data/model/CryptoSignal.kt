package com.devstudio.forexFusion.data.model

data class CryptoSignal(
    val pairName: String,
    val imageUrl: String,
    val date: String,
    val entryPrice: String,
    val type: String, // short for sell / long for buy
    val capital: String,
    val leverage: String,
    val takeProfit1: String,
    val takeProfit1Status: Int = 0, // 0 for not done yet and 1 for done
    val takeProfit2: String,
    val takeProfit2Status: Int = 0,
    val takeProfit3: String,
    val takeProfit3Status: Int = 0,
    val percentProfit1: String,
    val percentProfit2: String,
    val percentProfit3: String,
    val stopLose: String,
    val id: String = "0",
){
    constructor(): this(
        pairName = "",
        imageUrl = "",
        entryPrice = "",
        type = "",
        capital = "",
        leverage = "",
        takeProfit1 = "",
        takeProfit2 = "",
        takeProfit3 = "",
        stopLose = "",
        date = "",
        percentProfit1 = "",
        percentProfit2 = "",
        percentProfit3 = "",
    )
}
