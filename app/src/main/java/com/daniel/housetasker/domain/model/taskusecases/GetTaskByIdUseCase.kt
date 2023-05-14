package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getTaskById(id: String): List<TaskEntity>{
        return repository.getTaskById(id)
    }
}