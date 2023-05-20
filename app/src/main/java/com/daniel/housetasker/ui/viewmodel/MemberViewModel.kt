package com.daniel.housetasker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.domain.model.memberusecases.DeleteMemberByIdUseCase
import com.daniel.housetasker.domain.model.memberusecases.GetAllMembersUseCase
import com.daniel.housetasker.domain.model.memberusecases.GetMemberByIdUseCase
import com.daniel.housetasker.domain.model.memberusecases.InsertMemberUseCase
import com.daniel.housetasker.domain.model.memberusecases.ModifyPhotoMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val deleteMemberByIdUseCase: DeleteMemberByIdUseCase,
    private val getAllMembersUseCase: GetAllMembersUseCase,
    private val getMemberByIdUseCase: GetMemberByIdUseCase,
    private val insertMemberUseCase: InsertMemberUseCase,
    private val modifyPhotoMemberUseCase: ModifyPhotoMemberUseCase
): ViewModel(){

    val memberDataModel = MutableLiveData<List<MemberEntity>>()

    fun deleteMemberById(id:String){
        viewModelScope.launch {
            deleteMemberByIdUseCase.deleteMemberById(id)
        }
    }

    fun getAllMembers(){
        viewModelScope.launch {
            val members = getAllMembersUseCase.getAllMember()
            memberDataModel.value = members
        }
    }

    fun getMemberById(id: String){
        viewModelScope.launch {
            val members = getMemberByIdUseCase.getMemberById(id)
            memberDataModel.value = members
        }
    }

    fun insertMember(memberEntity: MemberEntity){
        viewModelScope.launch {
            insertMemberUseCase.insertMember(memberEntity)
        }
    }

    fun modifyPhotoMemberUseCase(id: String, newPhoto: String){
        viewModelScope.launch {
            modifyPhotoMemberUseCase.modifyPhotoMember(id,newPhoto)
        }
    }
}
