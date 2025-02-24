package com.ggperroni.challengemobiledev.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    @GET("tree")
    suspend fun getTree(
        @Header("Authorization") token: String
    ): List<TreeItemResponse>

    @POST("tree")
    suspend fun updateTree(
        @Header("Authorization") token: String,
        @Body request: UpdateTreeRequest,
    ): Response<TreeItemResponse>
}