package com.ggperroni.challengemobiledev.data.login

import com.ggperroni.challengemobiledev.domain.login.LoginState

interface LoginRepository {
    suspend fun login(username: String, password: String): LoginState<String>
    suspend fun saveUser(username: String, password: String)
}