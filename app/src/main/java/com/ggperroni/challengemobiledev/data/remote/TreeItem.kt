package com.ggperroni.challengemobiledev.data.remote

data class TreeItem(
    val id: Int,
    val name: String,
    val parent: Int?,
    val level: Int,
    val children: List<TreeItem> = emptyList()
)