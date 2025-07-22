package my.app.coffee.core.main

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val prefs: SharedPreferences,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = prefs.getString("access_token", null)
        Log.d("TOKEN", "$token")

        val requestBuilder = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            prefs.edit().remove("access_token").apply()
        }

        return response
    }
}