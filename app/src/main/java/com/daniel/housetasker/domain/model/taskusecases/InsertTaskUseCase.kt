package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun insertTask(task: TaskEntity){
        repository.insertTask(task)
    }
}