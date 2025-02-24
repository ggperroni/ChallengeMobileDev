package com.ggperroni.challengemobiledev.data.login

import com.ggperroni.challengemobiledev.data.local.dao.UserDao
import com.ggperroni.challengemobiledev.data.local.entity.UserEntity
import com.ggperroni.challengemobiledev.data.remote.ApiService
import com.ggperroni.challengemobiledev.domain.login.LoginState
import com.ggperroni.challengemobiledev.view.components.SecurePreferences
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val securePreferences: SecurePreferences,
    private val userDao: UserDao
) : LoginRepository {

    override suspend fun login(username: String, password: String): LoginState<String> {
        return try {
            val response = apiService.login(
                mapOf(
                    "username" to username,
                    "password" to password
                )
            )
            if (response.isSuccessful) {
                val token = "Bearer ${response.body()?.accessToken}"
                securePreferences.saveAuthToken(token)
                securePreferences.saveUsername(username)
                LoginState.Success(token)
            } else {
                LoginState.Error(
                    message = response.errorBody()?.string() ?: "Erro desconhecido",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            LoginState.Error(message = e.message ?: "Falha na conexão")
        }
    }

    override suspend fun saveUser(username: String, password: String) {
        userDao.insertUser(UserEntity(username = username, password = password))
    }
}