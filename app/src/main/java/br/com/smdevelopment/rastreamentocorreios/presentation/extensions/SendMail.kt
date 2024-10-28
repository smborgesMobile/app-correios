package br.com.smdevelopment.rastreamentocorreios.presentation.extensions

import android.content.Context
import android.content.Intent
import android.util.Log

fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: Exception) {
        Log.d("mail", e.toString())
    }
}