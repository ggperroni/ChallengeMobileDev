package com.ggperroni.challengemobiledev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tree_items")
data class TreeItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val parent: Int?,
    val level: Int
)