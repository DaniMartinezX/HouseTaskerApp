package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.databinding.ActivityCategoryManagerBinding

class CategoryManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddCategory() }
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showDialogAddCategory() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_category)

        val btnAddCategory : Button = dialog.findViewById(R.id.btnAddCategory)
        val etName : EditText = dialog.findViewById(R.id.etName)
        val etColor : EditText = dialog.findViewById(R.id.etColor)

        btnAddCategory.setOnClickListener { Toast.makeText(this,"Botón tocado",Toast.LENGTH_SHORT).show() }
        dialog.show()
    }
}