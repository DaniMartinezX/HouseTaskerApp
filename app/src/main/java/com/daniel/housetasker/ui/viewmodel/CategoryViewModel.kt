package com.daniel.housetasker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.domain.model.categoryusecases.DeleteCategoryByIdUseCase
import com.daniel.housetasker.domain.model.categoryusecases.GetAllCategoriesUseCase
import com.daniel.housetasker.domain.model.categoryusecases.InsertCategoryUseCase
import com.daniel.housetasker.domain.model.categoryusecases.ModifyColorCategoryUseCase
import com.daniel.housetasker.domain.model.categoryusecases.ModifyNameCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val deleteCategoryByIdUseCase: DeleteCategoryByIdUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val modifyColorCategoryUseCase: ModifyColorCategoryUseCase,
    private val modifyNameCategoryUseCase: ModifyNameCategoryUseCase,
): ViewModel() {

    val categoryDataModel = MutableLiveData<List<CategoryEntity>>()

    fun deleteCategoryById(id:String){
        viewModelScope.launch {
            deleteCategoryByIdUseCase.deleteCategoryById(id)
        }
    }

    fun getAllCategories(){
        viewModelScope.launch {
            val categories = getAllCategoriesUseCase.getAllCategories()
            categoryDataModel.value = categories
        }
    }

    fun insertCategory(categoryEntity: CategoryEntity){
        viewModelScope.launch {
            insertCategoryUseCase.insertCategory(categoryEntity)
        }
    }

    fun modifyColorCategory(id: String, newColor: String){
        viewModelScope.launch {
            modifyColorCategoryUseCase.modifyColorCategory(id,newColor)
        }
    }

    fun modifyNameCategory(id:String, newName: String) {
        viewModelScope.launch {
            modifyNameCategoryUseCase.modifyNameCategory(id, newName)
        }
    }
}