package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class DeleteAssignmentUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun deleteAssignment(id: String){
        repository.deleteAssignment(id)
    }
}