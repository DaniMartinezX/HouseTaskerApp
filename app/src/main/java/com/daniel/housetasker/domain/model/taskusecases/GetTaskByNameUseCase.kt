package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class GetTaskByNameUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getTaskByName(search: String): List<TaskEntity>{
        return repository.getTaskByName(search)
    }
}