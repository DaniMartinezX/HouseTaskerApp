package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class GetTasksByCategoryUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getTasksByCategory(id: String): List<TaskEntity>{
        return repository.getTasksByCategory(id)
    }
}