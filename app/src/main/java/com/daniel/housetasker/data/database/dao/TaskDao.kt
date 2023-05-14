package com.daniel.housetasker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniel.housetasker.data.database.entities.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    suspend fun getAllTask(): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE id = :search")
    suspend fun getTaskById(search:String): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE name LIKE :search")
    suspend fun getTaskByName(search: String): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task:TaskEntity)

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTask(id: String)

    @Query("SELECT * FROM task_table WHERE completed = 0")
    suspend fun getTaskUncompleted(): List<TaskEntity>

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks()

    @Query("SELECT * FROM task_table WHERE idCategory = :idCategory")
    suspend fun getTasksByCategory(idCategory: String): List<TaskEntity>
}