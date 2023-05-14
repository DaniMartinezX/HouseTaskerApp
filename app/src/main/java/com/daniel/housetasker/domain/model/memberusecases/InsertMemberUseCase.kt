package com.daniel.housetasker.domain.model.memberusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.MemberEntity
import javax.inject.Inject

class InsertMemberUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun insertMember(member: MemberEntity){
        repository.insertMember(member)
    }
}