package com.devstudio.forexFusion.ui.utils

import android.util.Patterns
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object Utils {

    // checking the email is valid or not

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun sendFeedback(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sultan.devstudio@gmail.com"))
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
        val phoneNumber = "03091737704" // Replace with your phone number
        val uri = Uri.parse("https://wa.me/$phoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
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