package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class GetUncompletedAssignmentCountForMemberUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getUncompletedAssignmentCountForMember(memberId: String): Int{
        return repository.getUncompletedAssignmentCountForMember(memberId)
    }
}