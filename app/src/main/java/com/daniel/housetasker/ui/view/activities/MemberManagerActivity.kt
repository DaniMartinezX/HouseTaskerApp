package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.daniel.housetasker.R
import com.daniel.housetasker.databinding.ActivityMemberManagerBinding

class MemberManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddMember() }
        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun showDialogAddMember() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_member)

        val btnAddMember : Button = dialog.findViewById(R.id.btnAddMember)
        val etName : EditText = dialog.findViewById(R.id.etName)
        val etPhoto : EditText = dialog.findViewById(R.id.etPhoto)

        btnAddMember.setOnClickListener { Toast.makeText(this,"Botón tocado", Toast.LENGTH_SHORT).show() }
        dialog.show()
    }
}