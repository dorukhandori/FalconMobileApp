package com.example.app

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveXcmzKey(xcmzKey: String) {
        sharedPreferences.edit().putString("xcmz_key", xcmzKey).apply()
    }

    fun getXcmzKey(): String? {
        return sharedPreferences.getString("xcmz_key", null)
    }

    fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit().putString("access_token", accessToken).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }
} 