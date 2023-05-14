package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import javax.inject.Inject

class GetAllAssignmentUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getAllAssignments(): List<AssignmentEntity>{
        return repository.getAllAssignments()
    }
}