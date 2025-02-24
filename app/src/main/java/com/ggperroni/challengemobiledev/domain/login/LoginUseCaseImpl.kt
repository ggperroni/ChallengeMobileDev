package com.ggperroni.challengemobiledev.domain.login

import com.ggperroni.challengemobiledev.data.login.LoginRepository

class LoginUseCaseImpl(
    private val repository: LoginRepository
) : LoginUseCase {
    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val result = repository.login(username, password)
            if (result is LoginState.Success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Credenciais inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUser(username: String, password: String): Result<Unit> {
        return try {
            repository.saveUser(username, password)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
