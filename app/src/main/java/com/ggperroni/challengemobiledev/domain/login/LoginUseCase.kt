package com.ggperroni.challengemobiledev.domain.login

interface LoginUseCase {
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun saveUser(username: String, password: String): Result<Unit>
}