package com.daniel.housetasker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daniel.housetasker.data.database.dao.AssignmentDao
import com.daniel.housetasker.data.database.dao.CategoryDao
import com.daniel.housetasker.data.database.dao.MemberDao
import com.daniel.housetasker.data.database.dao.TaskDao
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.data.database.entities.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class, MemberEntity::class, AssignmentEntity::class], version = 1)
abstract class HouseTaskerDatabase: RoomDatabase() {

    abstract fun getTaskDao():TaskDao

    abstract fun getCategoryDao():CategoryDao

    abstract fun getMemberDao():MemberDao

    abstract fun getAssignmentDao():AssignmentDao
}