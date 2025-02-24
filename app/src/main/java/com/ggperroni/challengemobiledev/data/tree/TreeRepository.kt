package com.ggperroni.challengemobiledev.data.tree

import com.ggperroni.challengemobiledev.data.remote.TreeItem
import com.ggperroni.challengemobiledev.data.remote.TreeItemResponse
import retrofit2.Response

interface TreeRepository {
    suspend fun getTree(): List<TreeItem>
    suspend fun updateTree(id: Int, name: String): Response<TreeItemResponse>
}