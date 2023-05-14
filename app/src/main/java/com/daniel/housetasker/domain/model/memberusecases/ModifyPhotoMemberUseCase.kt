package com.daniel.housetasker.domain.model.memberusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class ModifyPhotoMemberUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun modifyPhotoMember(id: String, newPhoto: String){
        repository.modifyPhotoMember(id, newPhoto)
    }
}