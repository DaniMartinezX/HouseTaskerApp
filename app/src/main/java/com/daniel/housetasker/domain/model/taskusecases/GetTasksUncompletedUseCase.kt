package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.TaskEntity
import javax.inject.Inject

class GetTasksUncompletedUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getTaskUncompleted(): List<TaskEntity>{
        return repository.getTaskUncompleted()
    }
}