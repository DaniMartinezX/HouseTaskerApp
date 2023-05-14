package com.daniel.housetasker.domain.model.taskusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun deleteTask(id: String){
        repository.deleteTask(id)
    }
}