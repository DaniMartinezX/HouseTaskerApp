package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.databinding.ActivityAssignmentManagerBinding
import com.daniel.housetasker.databinding.ActivityTaskManagerBinding

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
        val etMember : EditText = dialog.findViewById(R.id.etMember)
        val etAssignmentDate : EditText = dialog.findViewById(R.id.etAssignmentDate)

        btnAddAssignment.setOnClickListener { Toast.makeText(this,"Botón tocado", Toast.LENGTH_SHORT).show() }
        dialog.show()
    }
}