package com.devstudio.forexFusion

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppClass : Application() {

    companion object {
        // NOTE: Replace the below with your own ONESIGNAL_APP_ID
        const val ONESIGNAL_APP_ID = "0a916838-9015-4e1f-9ad4-355e7ae6c3e8"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this@AppClass
        OneSignal.initWithContext(this@AppClass, ONESIGNAL_APP_ID)
        FirebaseApp.initializeApp(this@AppClass)


        CoroutineScope(Dispatchers.IO).launch {


            // Verbose Logging set to help debug issues, remove before releasing your app.
//            OneSignal.Debug.logLevel = LogLevel.VERBOSE

//        OneSignal.SetLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG)

            // OneSignal Initialization


            val externalId =
                FirebaseAuth.getInstance().currentUser?.uid // You will supply the external user id to the OneSignal SDK
            externalId?.let {
                OneSignal.login(it)
            }


            // requestPermission will show the native Android notification permission prompt.
            // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
            OneSignal.Notifications.requestPermission(false)
        }

    }
}