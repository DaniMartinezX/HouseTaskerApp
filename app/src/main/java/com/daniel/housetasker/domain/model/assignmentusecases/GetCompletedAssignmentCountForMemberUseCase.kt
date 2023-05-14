package com.daniel.housetasker.domain.model.assignmentusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class GetCompletedAssignmentCountForMemberUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getCompletedAssignmentCountForMember(memberId: String): Int{
        return repository.getCompletedAssignmentCountForMember(memberId)
    }
}