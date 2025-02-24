package com.ggperroni.challengemobiledev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ggperroni.challengemobiledev.data.local.entity.TreeItemEntity

@Dao
interface TreeItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TreeItemEntity>)

    @Query("SELECT * FROM tree_items")
    suspend fun getAll(): List<TreeItemEntity>

    @Query("UPDATE tree_items SET name = :name WHERE id = :id")
    suspend fun updateName(id: Int, name: String)
}