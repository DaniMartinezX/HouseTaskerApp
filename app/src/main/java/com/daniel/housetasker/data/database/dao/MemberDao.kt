package com.daniel.housetasker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniel.housetasker.data.database.entities.MemberEntity

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberEntity)

    @Query("DELETE FROM member_table WHERE id = :id")
    suspend fun deleteMemberById(id: String)

    @Query("SELECT * FROM member_table")
    suspend fun getAllMember(): List<MemberEntity>

    @Query("SELECT * FROM member_table WHERE id = :id")
    suspend fun getMemberById(id: String): List<MemberEntity>

    @Query("UPDATE member_table SET photo = :newPhoto WHERE id = :id")
    suspend fun modifyPhotoMember(id: String, newPhoto: String)

}