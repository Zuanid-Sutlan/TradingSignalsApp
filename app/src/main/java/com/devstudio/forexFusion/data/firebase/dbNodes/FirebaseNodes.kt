package com.devstudio.forexFusion.data.firebase.dbNodes

sealed class FirebaseNodes(val node: String) {

    data object Crypto : FirebaseNodes("crypto_signals")

    data object Forex : FirebaseNodes("forex_signals")

    data object Event : FirebaseNodes("event_signals")

    data object Results : FirebaseNodes("results")

    data object Community : FirebaseNodes("community")

    data object Notification : FirebaseNodes("notification")



}