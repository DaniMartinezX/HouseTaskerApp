package com.daniel.housetasker.domain.model.categoryusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.CategoryEntity
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun insertCategory(category: CategoryEntity){
        repository.insertCategory(category)
    }
}