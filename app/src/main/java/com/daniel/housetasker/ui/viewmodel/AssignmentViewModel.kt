package com.daniel.housetasker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.housetasker.data.database.dao.AssignmentDao
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.domain.model.assignmentusecases.DeleteAssignmentUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.GetAllAssignmentUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.GetAssignmentByMemberUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.GetAssignmentUncompletedUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.GetCompletedAssignmentCountForMemberUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.GetUncompletedAssignmentCountForMemberUseCase
import com.daniel.housetasker.domain.model.assignmentusecases.InsertAssignmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao,
    private val deleteAssignmentUseCase: DeleteAssignmentUseCase,
    private val getAllAssignmentUseCase: GetAllAssignmentUseCase,
    private val getAssignmentByMemberUseCase: GetAssignmentByMemberUseCase,
    private val getAssignmentUncompletedUseCase: GetAssignmentUncompletedUseCase,
    private val getCompletedAssignmentCountForMemberUseCase: GetCompletedAssignmentCountForMemberUseCase,
    private val getUncompletedAssignmentCountForMemberUseCase: GetUncompletedAssignmentCountForMemberUseCase,
    private val insertAssignmentUseCase: InsertAssignmentUseCase,
): ViewModel(){

    val assignmentDataModel = MutableLiveData<List<AssignmentEntity>>()
    val completedUncompletedDataModel = MutableLiveData<Int>()

    fun deleteAssignment(id: String){
        viewModelScope.launch {
            deleteAssignmentUseCase.deleteAssignment(id)
        }
    }

    fun updateAssignmentCompletedStatus(assignment: AssignmentEntity) {
        viewModelScope.launch {
            assignmentDao.updateAssignment(assignment)
        }
    }

    fun getAllAssignments(){
        viewModelScope.launch {
            val assignments = getAllAssignmentUseCase.getAllAssignments()
            assignmentDataModel.value = assignments
        }
    }

    fun getAssignmentByMember(memberId: String){
        viewModelScope.launch {
            val assignments = getAssignmentByMemberUseCase.getAssignmentsByMember(memberId)
            assignmentDataModel.value = assignments
        }
    }

    fun getAssignmentUncompleted(){
        viewModelScope.launch {
            val assignments = getAssignmentUncompletedUseCase.getAssignmentUncompleted()
            assignmentDataModel.value = assignments
        }
    }

    fun getCompletedAssignmentCountForMember(memberId: String){
        viewModelScope.launch {
            val completed = getCompletedAssignmentCountForMemberUseCase.getCompletedAssignmentCountForMember(memberId)
            completedUncompletedDataModel.value = completed
        }
    }

    fun getUncompletedAssignmentCountForMember(memberId: String){
        viewModelScope.launch {
            val uncompleted = getUncompletedAssignmentCountForMemberUseCase.getUncompletedAssignmentCountForMember(memberId)
            completedUncompletedDataModel.value = uncompleted
        }
    }

    fun insertAssignment(assignment: AssignmentEntity){
        viewModelScope.launch {
            insertAssignmentUseCase.insertAssignment(assignment)
        }
    }
}