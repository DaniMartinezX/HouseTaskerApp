package com.daniel.housetasker.domain.model.categoryusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import javax.inject.Inject

class DeleteCategoryByIdUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun deleteCategoryById(id:String){
        repository.deleteCategoryById(id)
    }
}