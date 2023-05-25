package com.daniel.housetasker.domain.model.categoryusecases

import com.daniel.housetasker.data.HouseTaskerRepository
import com.daniel.housetasker.ui.view.objects.ColorObject
import javax.inject.Inject

class ModifyColorCategoryUseCase @Inject constructor(
    private val repository: HouseTaskerRepository
) {
    suspend fun modifyColorCategory(id: String, newColor: String){
        repository.modifyColorCategory(id, newColor)
    }
}