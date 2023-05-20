package com.daniel.housetasker.ui.view.activities

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.databinding.ActivityMemberManagerBinding
import com.daniel.housetasker.ui.viewmodel.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberManagerBinding
    //private val memberViewModel: MemberViewModel by ViewModels()


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

        btnAddMember.setOnClickListener { addMember(etName,etPhoto) }
        dialog.show()
    }

    private fun addMember(etName: EditText, etPhoto: EditText) {
        val nameM = etName.text.toString()
        if (nameM.isEmpty()){
            Toast.makeText(this,"You must fill all the fields to add",Toast.LENGTH_SHORT).show()
            etPhoto.setText("")
        } else {
            if (etPhoto.text.isEmpty()){
                showConfirmationDialogWithoutPhoto(nameM)
            }
            val photoM: String = etPhoto.text.toString()
            val member: MemberEntity = MemberEntity(
                name = nameM,
                photo = photoM
            )
            //memberViewModel.insertMember(member)
        }
    }

    private fun showConfirmationDialogWithoutPhoto(nameM: String) {
        val photo = ""
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val builder = AlertDialog.Builder(this)
            .setTitle("You did not set a photo")
            .setView(dialogView)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "Sí"
                // ...
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "No"
                // ...
            }
            .setCancelable(false)

        builder.show()
    }



}