package com.daniel.housetasker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.daniel.housetasker.data.database.entities.AssignmentEntity

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignment_table")
    suspend fun getAllAssignments(): List<AssignmentEntity>

    @Update
    suspend fun updateAssignment(assignment: AssignmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignment: AssignmentEntity)

    @Query("DELETE FROM assignment_table WHERE id = :id")
    suspend fun deleteAssignment(id: String)

    @Query("SELECT * FROM assignment_table WHERE memberId = :memberId")
    suspend fun getAssignmentsByMember(memberId: String): List<AssignmentEntity>

    @Query("SELECT * FROM assignment_table WHERE completed = 0")
    suspend fun getAssignmentUncompleted(): List<AssignmentEntity>

    //Cómputo de asignaciones completadas de un miembro
    @Query("SELECT COUNT(*) FROM assignment_table WHERE memberId = :memberId AND completed = 1")
    suspend fun getCompletedAssignmentCountForMember(memberId: String): Int

    //Cómputo de asignaciones incompletas de un miembro
    @Query("SELECT COUNT(*) FROM assignment_table WHERE memberId = :memberId AND completed = 0")
    suspend fun getUncompletedAssignmentCountForMember(memberId: String): Int
}