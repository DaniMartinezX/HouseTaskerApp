package com.daniel.housetasker.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "task_table",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["idCategory"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "idCategory") val idCategory: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "creation_date") val creationDate : Long,
    @ColumnInfo(name = "expiration_date") val expirationDate : Long,
    @ColumnInfo(name = "completed") val completed : Boolean
)