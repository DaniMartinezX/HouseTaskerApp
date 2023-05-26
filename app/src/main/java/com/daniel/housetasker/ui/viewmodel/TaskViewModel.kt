package com.daniel.housetasker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.domain.model.taskusecases.DeleteCompletedTasksUseCase
import com.daniel.housetasker.domain.model.taskusecases.DeleteTaskUseCase
import com.daniel.housetasker.domain.model.taskusecases.GetAllTasksUseCase
import com.daniel.housetasker.domain.model.taskusecases.GetTaskByIdUseCase
import com.daniel.housetasker.domain.model.taskusecases.GetTaskByNameUseCase
import com.daniel.housetasker.domain.model.taskusecases.GetTasksByCategoryUseCase
import com.daniel.housetasker.domain.model.taskusecases.GetTasksUncompletedUseCase
import com.daniel.housetasker.domain.model.taskusecases.InsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val deleteCompletedTasksUseCase: DeleteCompletedTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getTaskByNameUseCase: GetTaskByNameUseCase,
    private val getTasksByCategoryUseCase: GetTasksByCategoryUseCase,
    private val getTasksUncompletedUseCase: GetTasksUncompletedUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
): ViewModel(){

    val taskDataModel = MutableLiveData<List<TaskEntity>>()

    fun deletedCompleted(){
        viewModelScope.launch {
            deleteCompletedTasksUseCase.deleteCompletedTasks()
        }
    }

    fun deleteTask(id: String){
        viewModelScope.launch {
            deleteTaskUseCase.deleteTask(id)
        }
    }

    fun getAllTask(){
        viewModelScope.launch {
            val tasks = getAllTasksUseCase.getAllTasks()
            taskDataModel.value = tasks
        }
    }

    fun getTaskById(id: String){
        viewModelScope.launch {
            val tasks = getTaskByIdUseCase.getTaskById(id)
            taskDataModel.value = tasks
        }
    }

    fun getTaskByName(name: String){
        viewModelScope.launch {
            val tasks = getTaskByNameUseCase.getTaskByName(name)
            taskDataModel.value = tasks
        }
    }

    fun getTasksByCategory(id: String) {
        viewModelScope.launch {
            val tasks = getTasksByCategoryUseCase.getTasksByCategory(id)
            taskDataModel.value = tasks
        }
    }

    fun getTasksUncompleted() {
        viewModelScope.launch {
            val tasks = getTasksUncompletedUseCase.getTaskUncompleted()
            taskDataModel.value = tasks
        }
    }

    fun insertTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            insertTaskUseCase.insertTask(taskEntity)
        }
    }
}