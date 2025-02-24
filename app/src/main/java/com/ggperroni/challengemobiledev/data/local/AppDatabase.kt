package com.ggperroni.challengemobiledev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ggperroni.challengemobiledev.data.local.dao.TreeItemDao
import com.ggperroni.challengemobiledev.data.local.dao.UserDao
import com.ggperroni.challengemobiledev.data.local.entity.TreeItemEntity
import com.ggperroni.challengemobiledev.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, TreeItemEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun treeItemDao(): TreeItemDao
}