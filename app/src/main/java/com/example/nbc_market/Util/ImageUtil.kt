package com.example.nbc_market.Util

import android.net.Uri

object ImageUtil {

    fun getTemperatureImage(mannersTemperature: String): Uri {
        val temperature = mannersTemperature.split(" ")[0].toDoubleOrNull() ?: 0.0

        return when {
            temperature <= 30.0 -> Uri.parse("android.resource://com.example.nbc_market/drawable/manner_bad")
            temperature <= 50.0 -> Uri.parse("android.resource://com.example.nbc_market/drawable/manner_soso")
            temperature <= 75.0 -> Uri.parse("android.resource://com.example.nbc_market/drawable/manner_good")
            else -> Uri.parse("android.resource://com.example.nbc_market/drawable/manner_perfect")
        }
    }
}