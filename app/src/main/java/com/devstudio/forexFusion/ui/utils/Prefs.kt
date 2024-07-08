package com.devstudio.forexFusion.ui.utils

import android.preference.PreferenceManager
import com.devstudio.forexFusion.AppClass

object Prefs {

    private val sharedPreference = PreferenceManager.getDefaultSharedPreferences(AppClass.context)

    @JvmStatic
    var isDarkTheme : Boolean
        get() = sharedPreference.getBoolean("isDarkTheme", false)
        set(isDarkTheme){
            sharedPreference.edit().putBoolean("isDarkTheme",isDarkTheme).apply()
        }

    var notification : Boolean
        get() = sharedPreference.getBoolean("notification", false)
        set(isDarkTheme){
            sharedPreference.edit().putBoolean("notification",isDarkTheme).apply()
        }

    var firstTime: Boolean
        get() = sharedPreference.getBoolean("firstTime", true)
        set(value) {
            sharedPreference.edit().putBoolean("firstTime", value).apply()
        }
}