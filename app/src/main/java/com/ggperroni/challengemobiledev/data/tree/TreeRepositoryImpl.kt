package com.ggperroni.challengemobiledev.data.tree

import com.ggperroni.challengemobiledev.data.local.dao.TreeItemDao
import com.ggperroni.challengemobiledev.data.remote.ApiService
import com.ggperroni.challengemobiledev.data.remote.TreeItem
import com.ggperroni.challengemobiledev.data.remote.TreeItemResponse
import com.ggperroni.challengemobiledev.data.remote.UpdateTreeRequest
import com.ggperroni.challengemobiledev.data.remote.toDomain
import com.ggperroni.challengemobiledev.data.remote.toEntity
import com.ggperroni.challengemobiledev.view.components.SecurePreferences
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class TreeRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: TreeItemDao,
    private val prefs: SecurePreferences
) : TreeRepository {

    override suspend fun getTree(): List<TreeItem> {
        return try {
            val remoteItems =
                api.getTree(prefs.getAuthToken() ?: throw Exception("No token found"))
            val entities = remoteItems.flatMap { it.toEntity() }
            dao.insertAll(entities)
            dao.getAll().toDomain()
        } catch (e: Exception) {
            dao.getAll().toDomain()
        }
    }

    override suspend fun updateTree(id: Int, name: String): Response<TreeItemResponse> {
        return try {
            val request = UpdateTreeRequest(id, name)
            api.updateTree(
                token = prefs.getAuthToken() ?: throw Exception("No token found"),
                request = request
            )
        } catch (e: Exception) {
            Response.error(401, ResponseBody.create(null, e.message ?: "Unknown error"))
        }
    }
}