package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.databinding.ActivityAssignmentManagerBinding

class AssignmentManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignmentManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddAssignment() }
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showDialogAddAssignment() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_assignment)

        val btnAddAssignment : Button = dialog.findViewById(R.id.btnAddAssignment)
        val etTask : EditText = dialog.findViewById(R.id.etTask)
        val etMember : EditText = dialog.findViewById(R.id.etMember)
        val etAssignmentDate : EditText = dialog.findViewById(R.id.etAssignmentDate)



        btnAddAssignment.setOnClickListener { btnAddAssignmentFunction(etTask,etMember,etAssignmentDate) }
        dialog.show()
    }

    private fun btnAddAssignmentFunction(etTask: EditText, etMember: EditText, etAssignmentDate: EditText) {
        val task = etTask.text.toString()
        val member = etMember.text.toString()
        val date = etAssignmentDate.text.toString()
        if (task.isEmpty() || member.isEmpty() || date.isEmpty()){
            Toast.makeText(this,"You must fill all the fields to add",Toast.LENGTH_SHORT).show()
        } else {
            /*
            val assignment = AssignmentEntity(
                taskId = task.toLong(),
                assignmentDate = date.toLong(),
                completed = false,
                memberId = member.toLong()
            )
            */

            //todo crear asignación
            Toast.makeText(this,"Assignment created",Toast.LENGTH_SHORT).show()
            etTask.setText("")
            etMember.setText("")
            etAssignmentDate.setText("")
        }
    }
}