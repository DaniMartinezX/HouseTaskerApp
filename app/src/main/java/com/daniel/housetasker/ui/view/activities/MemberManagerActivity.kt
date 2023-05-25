package com.daniel.housetasker.ui.view.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.databinding.ActivityMemberManagerBinding
import com.daniel.housetasker.ui.view.adapters.MemberAdapter
import com.daniel.housetasker.ui.viewmodel.MemberViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class MemberManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberManagerBinding
    private lateinit var adapter: MemberAdapter
    private var membersList: List<MemberEntity> = mutableListOf()
    private val memberViewModel: MemberViewModel by viewModels()
    var photoUser: String = ""

    //Constante para solicitar el permiso de la cámara
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001



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
            memberViewModel.memberDataModel.observe(this@MemberManagerActivity){members ->
                members?.let {
                    membersList = it
                    adapter.updateList(membersList)
                }
            }
        }

        adapter = MemberAdapter(membersList,
            onMemberSelected = { position -> onMemberSelected(position) },
            onDeleteClicked = { position -> onDeleteClicked(position) }
        )

        binding.rvMembers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMembers.adapter = adapter

    }


    private fun onDeleteClicked(position: Int) {
        val id = membersList[position].id
        showConfirmationDialogDeleteMember(id.toString(),position)
    }

    private fun showConfirmationDialogDeleteMember(id: String, position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val builder = AlertDialog.Builder(this)
            .setTitle("Are you sure to delete the member?")
            .setView(dialogView)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "Sí"

                // Procedo a eliminarlo
                memberViewModel.deleteMemberById(id.toString())

                // Actualizo la lista y notifico al adapter,
                // borro el elemento de la lista a mano ya que va a ser borrado igual y
                // de esta manera actualizo al instante el listado de miembros.
                membersList = membersList.toMutableList().apply { removeAt(position) }
                adapter.updateList(membersList)

                Toast.makeText(this, "Successfully removed member", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                // Acciones a realizar si el usuario selecciona "No"
                Toast.makeText(this, "The member hasn't been deleted", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)

        builder.show()
    }


    private fun onMemberSelected(position:Int){
        val id = membersList[position].id
        val name = membersList[position].name
        val photo = membersList[position].photo
        showDialogMemberInfo(name,id.toString(),photo)
    }

    private fun showDialogMemberInfo(name:String, id: String, photo: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info_member)

        val btnBack : Button = dialog.findViewById(R.id.btnBack)
        val tvName : TextView = dialog.findViewById(R.id.tvName)
        val tvId : TextView = dialog.findViewById(R.id.tvId)
        val ivPhoto : ImageView = dialog.findViewById(R.id.ivPhoto)

        tvName.text = name
        tvId.text = id
        Picasso.get().load(photo).resize(90,90).into(ivPhoto)


        btnBack.setOnClickListener { dialog.hide() }
        dialog.show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de la cámara concedido
                openCamera()
            } else {
                // Permiso de la cámara denegado se muestra un mensaje al usuario
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Aquí puedes convertir el Bitmap en un String y cargarlo en Picasso
            val imageString = convertBitmapToString(imageBitmap)
            // Luego, puedes utilizar Picasso para cargar la imagen en tu ImageView
            photoUser = imageString
            //Picasso.get().load(imageString).into(imageView)
        }
    }

    //Convertir foto en string para cargarla con el picasso
    private fun convertBitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }






    private fun showDialogAddMember() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_member)

        val btnAddMember : Button = dialog.findViewById(R.id.btnAddMember)
        val etName : EditText = dialog.findViewById(R.id.etName)
        val btnPhoto : Button = dialog.findViewById(R.id.btnPhoto)

        btnPhoto.setOnClickListener {
            // Verifico y solicito el permiso de la cámara
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            } else {
                openCamera()
            }

        }


        btnAddMember.setOnClickListener {
            addMember(etName)
            dialog.hide()}
        dialog.show()
    }

    private fun addMember(etName: EditText) {
        val nameM = etName.text.toString()
        if (nameM.isEmpty()) {
            Toast.makeText(this, "You must fill all the fields to add", Toast.LENGTH_SHORT).show()
        } else {
            val member = MemberEntity(
                name = nameM,
                photo = photoUser
            )
            memberViewModel.insertMember(member)
            membersList = membersList + member // Agregar el nuevo miembro a la lista local
            adapter.updateList(membersList) // Actualizar el adaptador con la nueva lista
            Toast.makeText(this,"Member created",Toast.LENGTH_SHORT).show()
        }
    }

}