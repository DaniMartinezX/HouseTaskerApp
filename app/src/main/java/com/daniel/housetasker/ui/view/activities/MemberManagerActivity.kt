package com.daniel.housetasker.ui.view.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.databinding.ActivityMemberManagerBinding
import com.daniel.housetasker.ui.view.adapters.MemberAdapter
import com.daniel.housetasker.ui.viewmodel.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberManagerBinding
    private lateinit var adapter: MemberAdapter
    private var membersList: List<MemberEntity> = mutableListOf()
    private val memberViewModel: MemberViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.btnAdd.setOnClickListener { showDialogAddMember() }
        binding.btnBack.setOnClickListener { onBackPressed() }

        //Se cargan los miembros
        memberViewModel.getAllMembers()
        runOnUiThread {
            memberViewModel.memberDataModel.observe(this@MemberManagerActivity){
                it?.let { adapter.updateList(it) }
            }
        }
        membersList = memberViewModel.memberDataModel.value ?: emptyList()

        adapter = MemberAdapter(membersList) {onMemberSelected(it)}
        binding.rvMembers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMembers.adapter = adapter

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onMemberSelected(position:Int){
        val id = membersList[position].id
        val name = membersList[position].name
        showDialogMemberInfo(name,id.toString())
        adapter.notifyDataSetChanged()

    }

    private fun showDialogMemberInfo(name:String, id: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info_member)

        val btnBack : Button = dialog.findViewById(R.id.btnBack)
        val tvName : TextView = dialog.findViewById(R.id.tvName)
        val tvId : TextView = dialog.findViewById(R.id.tvId)

        tvName.text = name
        tvId.text = id

        btnBack.setOnClickListener { dialog.hide() }

    }

    private fun showDialogAddMember() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_member)

        val btnAddMember : Button = dialog.findViewById(R.id.btnAddMember)
        val etName : EditText = dialog.findViewById(R.id.etName)
        val etPhoto : EditText = dialog.findViewById(R.id.etPhoto)

        btnAddMember.setOnClickListener {
            addMember(etName,etPhoto)
            dialog.hide()}
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
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
            val member = MemberEntity(
                name = nameM,
                photo = photoM
            )
            Log.i("dani",member.toString())
            memberViewModel.insertMember(member)
            adapter.notifyDataSetChanged()
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