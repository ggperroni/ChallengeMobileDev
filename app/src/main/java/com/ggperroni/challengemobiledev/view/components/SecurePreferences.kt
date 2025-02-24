package com.ggperroni.challengemobiledev.view.components

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SecurePreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val AUTH_TOKEN = "access_token"
        private const val USERNAME = "username"
    }

    private val sharedPreferences = context.getSharedPreferences(
        "secure_storage",
        Context.MODE_PRIVATE
    )

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN, null)
    }

    fun clearAll() {
        sharedPreferences.edit().remove(AUTH_TOKEN).apply()
        sharedPreferences.edit().remove(USERNAME).apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(USERNAME, null)
    }

    fun saveUsername(username: String) {
        sharedPreferences.edit().putString(USERNAME, username).apply()
    }
}