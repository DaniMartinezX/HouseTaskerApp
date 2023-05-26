package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.databinding.ActivityCategoryManagerBinding
import com.daniel.housetasker.ui.view.adapters.CategoryAdapter
import com.daniel.housetasker.ui.view.adapters.ColorSpinnerAdapter
import com.daniel.housetasker.ui.view.objects.ColorList
import com.daniel.housetasker.ui.view.objects.ColorObject
import com.daniel.housetasker.ui.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryManagerBinding
    private lateinit var adapter: CategoryAdapter
    private var categoryList: List<CategoryEntity> = mutableListOf()
    private val categoryViewModel: CategoryViewModel by viewModels()
    lateinit var selectedColor: ColorObject
    var colorHex: String = "#000000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddCategory() }
        binding.btnBack.setOnClickListener { onBackPressed() }

        //Se cargan las categorías
        categoryViewModel.getAllCategories()
        runOnUiThread {
            categoryViewModel.categoryDataModel.observe(this@CategoryManagerActivity){ categories ->
                categories?.let {
                    categoryList = it
                    adapter.updateList(categoryList)
                }
            }
        }

        adapter = CategoryAdapter(categoryList,colorHex,
            onCategorySelected = { position -> onCategorySelected(position) },
            onDeleteClicked = { position -> onDeleteClicked(position) }
        )
        binding.rvCategories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvCategories.adapter = adapter
    }

    private fun onDeleteClicked(position: Int) {
        val id = categoryList[position].id
        showConfirmationDialogDeleteMember(id.toString(),position)
    }

    private fun showConfirmationDialogDeleteMember(id: String, position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val builder = AlertDialog.Builder(this)
            .setTitle("Are you sure to delete the category?")
            .setView(dialogView)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "Sí"

                // Procedo a eliminarlo
                categoryViewModel.deleteCategoryById(id.toString())

                // Actualizo la lista y notifico al adapter,
                // borro el elemento de la lista a mano ya que va a ser borrado igual y
                // de esta manera actualizo al instante el listado de categorías.
                categoryList = categoryList.toMutableList().apply { removeAt(position) }
                adapter.updateList(categoryList)

                Toast.makeText(this, "Successfully removed category", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "No"
                Toast.makeText(this, "The category hasn't been deleted", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)

        builder.show()
    }

    private fun onCategorySelected(position:Int){
        val id = categoryList[position].id
        val name = categoryList[position].name
        Toast.makeText(this,"Name of category: $name || Id: $id",Toast.LENGTH_SHORT).show()
    }




    private fun showDialogAddCategory() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_category)
        selectedColor = ColorList().defaultColor
        dialog.findViewById<Spinner?>(R.id.colorSpinner).apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColor())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedColor = ColorList().basicColor()[position]
                    var hexcolor = selectedColor.hex
                    colorHex = "#$hexcolor"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        val btnAddCategory : Button = dialog.findViewById(R.id.btnAddCategory)
        val etName : EditText = dialog.findViewById(R.id.etName)

        btnAddCategory.setOnClickListener {
            btnAddCategory(etName,colorHex)
            dialog.hide()}
        dialog.show()
    }

    private fun btnAddCategory(etName: EditText, etColor: String) {
        val name = etName.text.toString()
        val color = etColor.toString()
        if (name.isEmpty() || color.isEmpty()){
            Toast.makeText(this,"You must fill all the fields to add",Toast.LENGTH_SHORT).show()
        } else {
            val category = CategoryEntity(
                name = name,
                color = colorHex
            )
            Log.i("Daniel",colorHex)
            categoryViewModel.insertCategory(category)
            categoryList = categoryList + category // Agregar el nuevo miembro a la lista local
            adapter.updateList(categoryList)
            Toast.makeText(this,"Category created",Toast.LENGTH_SHORT).show()
        }
    }
}