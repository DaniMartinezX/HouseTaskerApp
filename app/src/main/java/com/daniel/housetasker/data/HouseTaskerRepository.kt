package com.daniel.housetasker.data

import com.daniel.housetasker.data.database.dao.AssignmentDao
import com.daniel.housetasker.data.database.dao.CategoryDao
import com.daniel.housetasker.data.database.dao.MemberDao
import com.daniel.housetasker.data.database.dao.TaskDao
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.ui.view.objects.ColorObject
import javax.inject.Inject

class HouseTaskerRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val memberDao: MemberDao,
    private val assignmentDao: AssignmentDao
) {
    // TASKS -----------------------------------------------------------------
    suspend fun getAllTasks(): List<TaskEntity>{
        return taskDao.getAllTask()
    }

    suspend fun getTaskById(id: String): List<TaskEntity>{
        return taskDao.getTaskById(id)
    }

    suspend fun getTaskByName(search: String): List<TaskEntity>{
        return taskDao.getTaskByName(search)
    }

    suspend fun insertTask(task: TaskEntity){
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(id: String){
        taskDao.deleteTask(id)
    }

    suspend fun getTaskUncompleted(): List<TaskEntity>{
        return taskDao.getTaskUncompleted()
    }

    suspend fun deleteCompletedTasks(){
        taskDao.deleteCompletedTasks()
    }

    suspend fun getTasksByCategory(id: String): List<TaskEntity>{
        return taskDao.getTasksByCategory(id)
    }
    //-----------------------------------------------------------------
    // CATEGORIES -----------------------------------------------------
    suspend fun getAllCategories(): List<CategoryEntity>{
        return categoryDao.getAllCategories()
    }

    suspend fun insertCategory(category: CategoryEntity){
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategoryById(id:String){
        categoryDao.deleteCategoryById(id)
    }

    suspend fun modifyNameCategory(id: String,newName: String){
        categoryDao.modifyNameCategory(id, newName)
    }

    suspend fun modifyColorCategory(id: String, newColor: String){
        categoryDao.modifyColorCategory(id,newColor)
    }
    //-----------------------------------------------------------------
    // MEMBERS -----------------------------------------------------
    suspend fun insertMember(member: MemberEntity){
        memberDao.insertMember(member)
    }

    suspend fun deleteMemberById(id: String){
        memberDao.deleteMemberById(id)
    }

    suspend fun getAllMember(): List<MemberEntity>{
        return memberDao.getAllMember()
    }

    suspend fun getMemberById(id: String): List<MemberEntity>{
        return memberDao.getMemberById(id)
    }

    suspend fun modifyPhotoMember(id: String, newPhoto: String){
        memberDao.modifyPhotoMember(id, newPhoto)
    }
    //-----------------------------------------------------------------
    // ASSIGNMENT -----------------------------------------------------
    suspend fun getAllAssignments(): List<AssignmentEntity>{
        return assignmentDao.getAllAssignments()
    }

    suspend fun insertAssignment(assignment: AssignmentEntity){
        assignmentDao.insertAssignment(assignment)
    }

    suspend fun deleteAssignment(id: String){
        assignmentDao.deleteAssignment(id)
    }

    suspend fun getAssignmentsByMember(memberId: String): List<AssignmentEntity>{
        return assignmentDao.getAssignmentsByMember(memberId)
    }

    suspend fun getAssignmentUncompleted(): List<AssignmentEntity>{
        return assignmentDao.getAssignmentUncompleted()
    }

    suspend fun getCompletedAssignmentCountForMember(memberId: String): Int{
        return assignmentDao.getCompletedAssignmentCountForMember(memberId)
    }

    suspend fun getUncompletedAssignmentCountForMember(memberId: String): Int{
        return assignmentDao.getUncompletedAssignmentCountForMember(memberId)
    }
}