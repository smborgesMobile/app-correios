package br.com.smdevelopment.rastreamentocorreios.presentation.utils

fun String.alphaNumericOnly(): String {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}