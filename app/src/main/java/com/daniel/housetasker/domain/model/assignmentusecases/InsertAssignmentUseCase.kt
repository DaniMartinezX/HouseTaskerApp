package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import javax.inject.Inject

class InsertAssignmentUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun insertAssignment(assignment: AssignmentEntity){
        repository.insertAssignment(assignment)
    }
}