package com.daniel.housetasker.domain.model.memberusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.MemberEntity
import javax.inject.Inject

class GetMemberByIdUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun getMemberById(id: String): List<MemberEntity>{
        return repository.getMemberById(id)
    }
}