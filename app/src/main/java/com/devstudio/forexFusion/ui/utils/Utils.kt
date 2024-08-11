package com.devstudio.forexFusion.ui.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

object Utils {

    // checking the email is valid or not

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun sendFeedback(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("forexfusion.help@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback of Forex Fusion")
            putExtra(Intent.EXTRA_TEXT, "Please write your feedback here.")
        }

        context.startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
    }

    fun openWhatsAppDeveloper(context: Context) {
        val phoneNumber = "03474293781" // Replace with your phone number
        val uri = Uri.parse("https://wa.me/$phoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppTrader(context: Context){
        val phoneNumber = "03436117105" // Replace with your phone number
        val uri = Uri.parse("https://wa.me/$phoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // format coin price to as needed decimal points
    fun formatDouble(value: Double): String {
        return /*if (value >= 1000){
        String.format("%.2f", value)
    }else if (value >= 100){
        String.format("%.2f", value)
    }else */if (value > 9.99){
            String.format("%.2f", value)
        }else if (value > 0.99){
            String.format("%.3f", value)
        }else if (value > 0.01){
            String.format("%.4f", value)
        } else if (value > 0.0001){
            String.format("%.5f", value)
        }else {
            String.format("%.6f", value)
        }
    }

    // change status bar text color
    fun statusBarTextColorDark(activity: Activity){
        // For light status bar text
//        activity.window.decorView.se;
    }

    fun openAppInPlayStore(context: Context) {
        val appPackageName = context.packageName
        try {
            // Try to open the Play Store app first
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: Exception) {
            // Fallback to opening the Play Store in the browser
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }




    // for gradient angle
    fun calculateGradientOffsets(
        width: Float,
        height: Float,
        angleDegrees: Float
    ): Pair<Offset, Offset> {
        val angleRadians = Math.toRadians(angleDegrees.toDouble())
        val x = (cos(angleRadians) * width).toFloat()
        val y = (sin(angleRadians) * height).toFloat()
        return Pair(Offset(0f, 0f), Offset(x, y))
    }

}