package com.daniel.housetasker.domain.model.memberusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class DeleteMemberByIdUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun deleteMemberById(id: String){
        repository.deleteMemberById(id)
    }
}