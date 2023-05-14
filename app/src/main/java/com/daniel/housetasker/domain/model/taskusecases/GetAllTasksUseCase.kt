package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getAllTasks(): List<TaskEntity>{
        return repository.getAllTasks()
    }
}