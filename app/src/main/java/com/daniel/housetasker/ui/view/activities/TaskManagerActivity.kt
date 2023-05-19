package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.databinding.ActivityTaskManagerBinding

class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddTask() }
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showDialogAddTask() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_task)

        val btnAddTask : Button = dialog.findViewById(R.id.btnAddTask)
        val etCategory : EditText = dialog.findViewById(R.id.etCategory)
        val etExpirationDate : EditText = dialog.findViewById(R.id.etExpirationDate)
        val etDescription : EditText = dialog.findViewById(R.id.etDescription)

        btnAddTask.setOnClickListener { Toast.makeText(this,"Botón tocado", Toast.LENGTH_SHORT).show() }
        dialog.show()
    }



}