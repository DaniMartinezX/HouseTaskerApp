package com.daniel.housetasker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniel.housetasker.data.database.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("DELETE FROM category_table WHERE id = :id")
    suspend fun deleteCategoryById(id: String)

    @Query("UPDATE category_table SET name = :newName WHERE id = :id")
    suspend fun modifyNameCategory(id: String, newName: String)

    @Query("UPDATE category_table SET color = :newColor WHERE id = :id")
    suspend fun modifyColorCategory(id: String, newColor: String)
}