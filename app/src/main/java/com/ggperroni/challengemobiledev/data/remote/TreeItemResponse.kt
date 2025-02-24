package com.ggperroni.challengemobiledev.data.remote

import com.ggperroni.challengemobiledev.data.local.entity.TreeItemEntity

data class TreeItemResponse(
    val id: Int,
    val name: String,
    val parent: Int?,
    val level: Int
)

fun TreeItemResponse.toEntity(): List<TreeItemEntity> {
    val current = TreeItemEntity(
        id = this.id,
        name = this.name,
        parent = this.parent,
        level = this.level
    )

    return listOf(current)
}

fun List<TreeItemEntity>.toDomain(): List<TreeItem> {
    return this.map { it.toDomain() }
}

private fun TreeItemEntity.toDomain(): TreeItem {
    return TreeItem(
        id = id,
        name = name,
        parent = parent,
        level = level
    )
}