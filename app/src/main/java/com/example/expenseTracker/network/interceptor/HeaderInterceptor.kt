package com.example.expenseTracker.network.interceptor

import com.example.expenseTracker.network.SessionManager
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mutableHeaders: MutableMap<String, String> =
            chain
                .request()
                .headers
                .toMap()
                .toMutableMap()

        /**
         * Constant reoccuring headers to be placed here
         */
        if (!mutableHeaders.containsKey("Accept")) {
            mutableHeaders["Accept"] = "application/json"
        }
        if (!mutableHeaders.containsKey("Content-Type")) {
            mutableHeaders["Content-Type"] = "application/json"
        }

        // Retrieve the authentication token
        val authToken = sessionManager.getToken()
        if (authToken.isNotEmpty()) {
            mutableHeaders["Authorization"] = "Bearer $authToken"
        }

        // Build headers
        val headerBuilder = Headers.Builder()
        for ((key, value) in mutableHeaders.entries) {
            headerBuilder.add(key, value)
        }

        val request = chain.request().newBuilder()
            .headers(headerBuilder.build())
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {  // Unauthorized → Token expired
            sessionManager.clearSession() // Clear session data
            sessionManager.triggerLogout() // Notify the app to logout
        }
        return response
    }
}
