package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.databinding.ActivityTaskManagerBinding
import com.daniel.housetasker.ui.view.adapters.TaskAdapter
import com.daniel.housetasker.ui.viewmodel.CategoryViewModel
import com.daniel.housetasker.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding
    private lateinit var adapter: TaskAdapter
    private var tasksList: List<TaskEntity> = mutableListOf()
    private val taskViewModel: TaskViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var categoryListEnt: List<CategoryEntity> = mutableListOf()

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

        categoryViewModel.getAllCategories()
        categoryViewModel.categoryDataModel.observe(this) { categories ->
            categories?.let {
                categoryListEnt = it
            }
        }
        adapter = TaskAdapter(tasksList,
            onTaskSelected = {position -> onTaskSelected(position)},
            onDeleteClicked = {position -> onDeleteClicked(position)},
            onStatusClicked = {position -> onStatusClicked(position)},
        )
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
        val categoryId = tasksList[position].idCategory
        val selectedCategoryName = categoryListEnt.firstOrNull { it.id == categoryId}?.name
        if (selectedCategoryName != null) {
            showDialogTaskInfo(name, description, fechaCreacion.toString(), fechaFinal.toString(), status,selectedCategoryName)
        }
    }

    private fun showDialogTaskInfo(name: String, description: String, creationDate: String, expirationDate: String, status: Boolean, category: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info_task)

        val btnBack : Button = dialog.findViewById(R.id.btnBack)
        val tvName : TextView = dialog.findViewById(R.id.tvName)
        val tvCreationDate : TextView = dialog.findViewById(R.id.tvCreationDate)
        val tvExpirationDate : TextView = dialog.findViewById(R.id.tvExpirationDate)
        val tvStatus : TextView = dialog.findViewById(R.id.tvStatus)
        val tvDescription : TextView = dialog.findViewById(R.id.tvDescription)
        val tvCategory : TextView = dialog.findViewById(R.id.tvCategory)

        tvName.text = name
        tvCreationDate.text = creationDate
        tvExpirationDate.text = expirationDate
        if (!status){
            tvStatus.text = "Uncompleted"
        } else {
            tvStatus.text = "Completed"
        }
        tvDescription.text = description
        tvCategory.text = category

        btnBack.setOnClickListener { dialog.hide() }
        dialog.show()

    }

    private fun onDeleteClicked(position: Int) {
        val id = tasksList[position].id
        showConfirmationDialogDeleteTask(id.toString(),position)
    }

    private fun onStatusClicked(position: Int){
        tasksList[position].completed = !tasksList[position].completed
        adapter.updateList(tasksList)
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

        var idCategory = ""

        val spinnerCategory: Spinner = dialog.findViewById(R.id.spinnerCategory)

        val categoryList = categoryListEnt.map { categoryEntity -> categoryEntity.name }
        val adapterCategory = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapterCategory


        var selectedDateInMillisReal: Long = 0

        val dateExpiration: DatePicker = dialog.findViewById(R.id.dateExpiration)
        var calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        dateExpiration.init(currentYear, currentMonth, currentDay) { datePicker, year, month, day ->
            // Se ejecuta cuando se selecciona una fecha
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val selectedDateInMillis = selectedDate.timeInMillis
            selectedDateInMillisReal = selectedDateInMillis
        }
        dateExpiration.minDate = calendar.timeInMillis

        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val etName: EditText = dialog.findViewById(R.id.etName)
        val categorySpinner: Spinner = dialog.findViewById<Spinner?>(R.id.spinnerCategory)
        val etDescription: EditText = dialog.findViewById(R.id.etDescription)

        val day = dateExpiration.dayOfMonth
        val month = dateExpiration.month
        val year = dateExpiration.year

        calendar = Calendar.getInstance()
        calendar.set(year, month, day)


        btnAddTask.setOnClickListener {
            val categoryName = categorySpinner.selectedItem.toString()

            //Saco el id de la categoría con ese nombre
            val selectedCategoryId = categoryListEnt.firstOrNull { it.name == categoryName}?.id

            Log.i("Daniel", "$categoryName : ${selectedCategoryId.toString()}")
            addTask(etName,selectedCategoryId.toString(),selectedDateInMillisReal.toString(),etDescription)
            dialog.hide()}
        dialog.show()
        }

    private fun addTask(
        etName: EditText,
        etCategory: String,
        etExpirationDate: String,
        etDescription: EditText
    ) {
        val idCategory: Long = etCategory.toLong()
        val date = etExpirationDate.toLong()
        val name = etName.text.toString()
        val description = etDescription.text.toString()
        //Cojo la fecha actual y la transformo a long para guardarla en la BBDD
        val initialDate: Date = Date()
        val initialDateLong = initialDate.time
        if (name.isEmpty() || idCategory.toString().isEmpty() || date.toString().isEmpty()) {
            Toast.makeText(
                this,
                "You must fill 'name', 'category' and 'date' at least",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val task = TaskEntity(
                idCategory = idCategory,
                name = name,
                description = description,
                creationDate = initialDateLong,
                expirationDate = date,
                completed = false
            )
            taskViewModel.insertTask(task)
            tasksList = tasksList + task
            adapter.updateList(tasksList)
            Toast.makeText(this, "Task created", Toast.LENGTH_SHORT).show()
            Log.i("daniel", task.toString())
        }
    }

    }
