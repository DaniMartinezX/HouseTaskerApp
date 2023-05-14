package com.daniel.housetasker.domain.model.categoryusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class ModifyNameCategoryUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun modifyNameCategory(id: String,newName: String){
        repository.modifyNameCategory(id, newName)
    }
}