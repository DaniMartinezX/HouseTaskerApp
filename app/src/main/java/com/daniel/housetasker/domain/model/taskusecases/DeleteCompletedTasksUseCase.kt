package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class DeleteCompletedTasksUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun deleteCompletedTasks(){
        repository.deleteCompletedTasks()
    }
}