package com.daniel.housetasker.domain.model.categoryusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.data.database.entities.CategoryEntity
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
){
    suspend fun getAllCategories(): List<CategoryEntity>{
        return repository.getAllCategories()
    }
}