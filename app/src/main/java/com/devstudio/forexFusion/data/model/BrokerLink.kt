package com.devstudio.forexFusion.data.model

data class BrokerLink(
    val title: String,
    val imageUrl: String,
    val link: String,
    val id: String = "0"
){
    constructor() : this("", "", "")
}
