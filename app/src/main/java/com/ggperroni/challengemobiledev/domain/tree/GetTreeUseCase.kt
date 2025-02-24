package com.ggperroni.challengemobiledev.domain.tree

import com.ggperroni.challengemobiledev.data.remote.TreeItem
import com.ggperroni.challengemobiledev.data.remote.TreeItemResponse
import com.ggperroni.challengemobiledev.data.tree.TreeRepository
import com.ggperroni.challengemobiledev.data.tree.TreeRepositoryImpl

class GetTreeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(): List<TreeItem> = repository.getTree()
}