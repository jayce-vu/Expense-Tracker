package com.example.expenseTracker.network

import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val encryptedPreferenceManager: EncryptedPreferenceManager) {
    private val logoutTrigger = MutableSharedFlow<Unit>()

    fun saveToken(token: String) {
        encryptedPreferenceManager.setToken(token)
    }

    fun clearSession() {
        encryptedPreferenceManager.setToken("")
    }

    fun getToken(): String {
        return encryptedPreferenceManager.getToken()
    }

    fun triggerLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            logoutTrigger.emit(Unit)
        }
    }

    fun observeLogout(): Flow<Unit> = logoutTrigger
}