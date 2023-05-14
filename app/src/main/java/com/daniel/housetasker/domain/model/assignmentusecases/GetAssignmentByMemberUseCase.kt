package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import javax.inject.Inject

class GetAssignmentByMemberUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getAssignmentsByMember(memberId: String): List<AssignmentEntity>{
        return repository.getAssignmentsByMember(memberId)
    }
}