package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.databinding.ActivityTaskManagerBinding
import com.daniel.housetasker.ui.view.adapters.TaskAdapter
import com.daniel.housetasker.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding
    private lateinit var adapter: TaskAdapter
    private var tasksList: List<TaskEntity> = mutableListOf()
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddTask() }
        binding.btnBack.setOnClickListener { onBackPressed() }

        taskViewModel.getAllTask()
        runOnUiThread {
            taskViewModel.taskDataModel.observe(this@TaskManagerActivity){tasks ->
                tasks?.let {
                    tasksList = it
                    adapter.updateList(tasksList)
                }
            }
        }
        adapter = TaskAdapter(tasksList,
        onTaskSelected = {position -> onTaskSelected(position)},
        onDeleteClicked = {position -> onDeleteClicked(position)})

        binding.rvTasks.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvTasks.adapter = adapter
    }

    private fun onTaskSelected(position: Int) {
        val name = tasksList[position].name
        val description = tasksList[position].description

        val creationDate = tasksList[position].creationDate
        val fechaCreacion = Date(creationDate)

        val expirationDate = tasksList[position].expirationDate
        val fechaFinal = Date(expirationDate)
        val status = tasksList[position].completed
        //TODO categoria
        showDialogTaskInfo(name, description, fechaCreacion.toString(), fechaFinal.toString(), status)
    }

    private fun showDialogTaskInfo(name: String, description: String, creationDate: String, expirationDate: String, status: Boolean) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info_task)

        val btnBack : Button = dialog.findViewById(R.id.btnBack)
        val tvName : TextView = dialog.findViewById(R.id.tvName)
        val tvCreationDate : TextView = dialog.findViewById(R.id.tvCreationDate)
        val tvExpirationDate : TextView = dialog.findViewById(R.id.tvExpirationDate)
        val tvStatus : TextView = dialog.findViewById(R.id.tvStatus)
        val tvDescription : TextView = dialog.findViewById(R.id.tvDescription)

        tvName.text = name
        tvCreationDate.text = creationDate
        tvExpirationDate.text = expirationDate
        tvStatus.text = status.toString()
        tvDescription.text = description

        btnBack.setOnClickListener { dialog.hide() }
        dialog.show()

    }

    private fun onDeleteClicked(position: Int) {
        val id = tasksList[position].id
        showConfirmationDialogDeleteTask(id.toString(),position)
    }

    private fun showConfirmationDialogDeleteTask(id: String, position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val builder = AlertDialog.Builder(this)
            .setTitle("Delete task")
            .setView(dialogView)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                taskViewModel.deleteTask(id)
                tasksList = tasksList.toMutableList().apply { removeAt(position) }
                adapter.updateList(tasksList)
                Toast.makeText(this, "Successfully removed task", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this, "The task hasn't been deleted", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
        builder.show()
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
        val idCategory: Long = etCategory.text.toString().toLong()
        val date = etExpirationDate.text.toString().toLong()
        val name = etName.text.toString()
        val description = etDescription.text.toString()
        //Cojo la fecha actual y la transformo a long para guardarla en la BBDD
        val initialDate: Date = Date()
        val initialDateLong = initialDate.time
        if (name.isEmpty() || idCategory.toString().isEmpty() || date.toString().isEmpty()){
            Toast.makeText(this,"You must fill 'name', 'category' and 'date' at least", Toast.LENGTH_SHORT).show()
        } else {
            val task = TaskEntity(
                idCategory = idCategory,
                name = name,
                description = description,
                creationDate = initialDateLong,
                expirationDate = date,
                completed = false
            )
            //TODO añadir task
            Toast.makeText(this,"Task created", Toast.LENGTH_SHORT).show()
        }
    }


}