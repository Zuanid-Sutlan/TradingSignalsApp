package com.devstudio.forexFusion.data.model

data class EventSignal(
    val pairName: String,
    val imageUrl: String,
    val date: String,
    val entryTime: String,
    val direction: String,
    val capital: String,
    val platform: String,
    val timeFrame: String = "",
    val stopLoss: String = "",
    val takeProfit: String = "",
    val accuracy: String,
    val mtgTime: String = "",
    val mtgNeed: String = "",
    val id: String = "0"
){
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "")
}
