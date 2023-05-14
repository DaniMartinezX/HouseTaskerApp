package com.daniel.housetasker.domain.model.memberusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.MemberEntity
import javax.inject.Inject

class GetAllMembersUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getAllMember(): List<MemberEntity>{
        return repository.getAllMember()
    }
}