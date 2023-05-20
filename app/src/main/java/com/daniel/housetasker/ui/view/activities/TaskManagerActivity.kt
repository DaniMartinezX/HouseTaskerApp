package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.TaskEntity
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
        val etName : EditText = dialog.findViewById(R.id.etName)
        val etCategory : EditText = dialog.findViewById(R.id.etCategory)
        val etExpirationDate : EditText = dialog.findViewById(R.id.etExpirationDate)
        val etDescription : EditText = dialog.findViewById(R.id.etDescription)

        btnAddTask.setOnClickListener { addTask(etName,etCategory,etExpirationDate,etDescription) }
        dialog.show()
    }

    private fun addTask(etName: EditText, etCategory: EditText, etExpirationDate: EditText, etDescription: EditText) {
        val category = etCategory.text.toString()
        val date = etExpirationDate.text.toString()
        val name = etName.text.toString()
        val description = etDescription.text.toString()
        if (name.isEmpty() || category.isEmpty() || date.isEmpty()){
            Toast.makeText(this,"You must fill 'name', 'category' and 'date' at least", Toast.LENGTH_SHORT).show()
        } else {
            /*
            val task = TaskEntity(
                idCategory = category.toLong(),
                name = name,
                description = description,
                creationDate = 1,
                expirationDate = 2,
                completed = false

            )
             */

            Toast.makeText(this,"Task created", Toast.LENGTH_SHORT).show()
            etCategory.setText("")
            etExpirationDate.setText("")
            etDescription.setText("")
        }
    }


}