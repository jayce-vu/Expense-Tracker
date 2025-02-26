package com.example.expenseTracker.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.expenseTracker.R
import com.example.expenseTracker.network.NetworkStatus
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class NetworkConnectionInterceptor(
    private val context: Context,
) : Interceptor {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun intercept(chain: Interceptor.Chain): Response {
        when (getCurrentNetworkStatus()) {
            NetworkStatus.Disconnected -> {
                Log.d("NETWORK", "Network status: Disconnected")
                throw NoConnectivityException(context)
            }

            else -> {
                Log.d("NETWORK", "Network status: Connected")
                val builder = chain.request().newBuilder()
                return chain.proceed(builder.build())
            }
        }
    }

    private fun getCurrentNetworkStatus(): NetworkStatus {
        return try {
            connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .let { connected ->
                    when (connected) {
                        true -> NetworkStatus.Connected
                        else -> NetworkStatus.Disconnected
                    }
                }
        } catch (e: Exception) {
            Log.d("NETWORK", "exception thrown: ${e.localizedMessage}")
            return NetworkStatus.Disconnected
        }
    }
}

class NoConnectivityException(
    private val context: Context,
) : IOException() {
    override val message: String
        get() = context.getString(R.string.no_internet_connection)

    override fun getLocalizedMessage(): String = context.getString(R.string.no_internet_connection)
}
