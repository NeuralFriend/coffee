package my.app.coffee.core.main

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val prefs: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = prefs.getString("access_token", null)
        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}