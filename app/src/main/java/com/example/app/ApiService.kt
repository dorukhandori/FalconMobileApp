package com.example.app

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class ApiService(private val preferencesHelper: PreferencesHelper) {
    private val client = OkHttpClient()

    fun login(customerCode: String, password: String, callback: (Boolean) -> Unit) {
        val xcmzKey = preferencesHelper.getXcmzKey() ?: return callback(false)

        val json = JSONObject()
        json.put("customerCode", customerCode)
        json.put("password", password)

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url("http://testapi.epic-soft.net/v1/Login/token")
            .post(requestBody)
            .addHeader("accept", "*/*")
            .addHeader("xcmzkey", xcmzKey)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val accessToken = JSONObject(responseBody).getString("accessToken")
                    preferencesHelper.saveAccessToken(accessToken)
                    callback(true)
                } else {
                    callback(false)
                }
            }
        })
    }
} 