package com.daniel.housetasker.ui.view.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.LocusId
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.databinding.FragmentHomeBinding
import com.daniel.housetasker.ui.view.activities.CategoryManagerActivity
import com.daniel.housetasker.ui.view.activities.MemberManagerActivity
import com.daniel.housetasker.ui.view.activities.TaskManagerActivity
import com.daniel.housetasker.ui.view.adapters.AssignmentAdapter
import com.daniel.housetasker.ui.viewmodel.AssignmentViewModel
import com.daniel.housetasker.ui.viewmodel.MemberViewModel
import com.daniel.housetasker.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val assignmentViewModel: AssignmentViewModel by viewModels()
    private val memberViewModel: MemberViewModel by viewModels()
    private val taskViewModel: TaskViewModel by viewModels()
    private var assignmentList: List<AssignmentEntity> = mutableListOf()
    private var numberAssignments: Int = 0
    private lateinit var adapter: AssignmentAdapter
    private var memberList: List<MemberEntity> = mutableListOf()
    private var taskList: List<TaskEntity> = mutableListOf()
    


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using the binding object
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener { showDialog() }

        assignmentViewModel.getAllAssignments()
        assignmentViewModel.assignmentDataModel.observe(viewLifecycleOwner){assignments ->
            assignments?.let {
                assignmentList = it
            }
        }

        //Cargando listas para la creación de assignments
        memberViewModel.getAllMembers()
        memberViewModel.memberDataModel.observe(viewLifecycleOwner){members ->
            members?.let {
                memberList = it
                taskViewModel.getAllTask()
            }
        }
        taskViewModel.taskDataModel.observe(viewLifecycleOwner){
            it?.let {
                taskList = it
                adapter = AssignmentAdapter(assignmentList, memberList, taskList,
                    onDeleteClicked = {position -> onDeleteClicked(position)},
                    onStatusClicked = {position -> onStatusClicked(position)},
                )
                binding.rvAssignments.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
                binding.rvAssignments.adapter = adapter
                adapter.updateList(assignmentList)
            }
        }



        binding.btnCreateAssignment.setOnClickListener { showDialogAddAssignment() }
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    private fun showDialogAddAssignment() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_assignment)

        // Para la creación de assignments
        var selectedTaskEntity: TaskEntity? = null
        var selectedMemberEntity: MemberEntity? = null

        val btnCreate: Button = dialog.findViewById(R.id.btnCreate)

        val memberNamesList = memberList.map { memberEntity -> memberEntity.name }
        val taskNamesList = taskList.map { taskEntity -> taskEntity.name }

        // Crear un ArrayAdapter con los elementos y un diseño predefinido para la apariencia de la lista desplegable
        val adapterArray = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, taskNamesList)
        val spinner: Spinner = dialog.findViewById(R.id.spinnerTasks)
        spinner.adapter = adapterArray
        val adapterMiembro = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, memberNamesList)
        val spinnerMiembros: Spinner = dialog.findViewById(R.id.spinnerMembers)
        spinnerMiembros.adapter = adapterMiembro

        // Manejar la selección de tarea
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedTaskEntity = taskList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedTaskEntity = null
            }
        }

        // Manejar la selección de miembro
        spinnerMiembros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedMemberEntity = memberList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedMemberEntity = null
            }
        }

        btnCreate.setOnClickListener {
            // Verificar si se ha seleccionado una tarea
            if (selectedTaskEntity != null) {
                val idTask = selectedTaskEntity!!.id
                val assignmentDate = selectedTaskEntity!!.expirationDate
                val statusTask = selectedTaskEntity?.completed ?: false
                val idMember = selectedMemberEntity?.id

                val assignment = AssignmentEntity(
                    taskId = idTask,
                    assignmentDate = assignmentDate , // Asignar valor predeterminado si es nulo
                    completed = statusTask , // Asignar valor predeterminado si es nulo
                    memberId = idMember ?: 0 // Asignar valor predeterminado si es nulo
                )

                assignmentViewModel.insertAssignment(assignment)
                assignmentList = assignmentList + assignment
                adapter.updateList(assignmentList)
                Toast.makeText(requireContext(), "Assignment created", Toast.LENGTH_SHORT).show()
                Log.i("daniel", assignment.toString())
            } else {
                // Manejar caso cuando no se ha seleccionado una tarea
                Toast.makeText(requireContext(), "Please select a task", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun onStatusClicked(position: Int) {
        val assignment = assignmentList[position]
        assignment.completed = !assignment.completed

        // Actualiza el estado completed de la asignación en el ViewModel y en la base de datos
        assignmentViewModel.updateAssignmentCompletedStatus(assignment)

        adapter.notifyItemChanged(position)
    }


    private fun onDeleteClicked(position: Int) {
        val id = assignmentList[position].id
        showConfirmationDialogDeleteAssignment(id.toString(), position)
    }

    private fun showConfirmationDialogDeleteAssignment(id: String, position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Delete assignment")
            .setView(dialogView)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                assignmentViewModel.deleteAssignment(id)
                assignmentList = assignmentList.toMutableList().apply { removeAt(position) }
                adapter.updateList(assignmentList)
                Toast.makeText(requireContext(), "Successfully removed task", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(requireContext(), "The task hasn't been deleted", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
        builder.show()
    }



    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add)

        val cvTask : CardView = dialog.findViewById(R.id.cvTask)
        val cvMember : CardView = dialog.findViewById(R.id.cvMember)
        val cvCategory : CardView = dialog.findViewById(R.id.cvCategory)

        cvTask.setOnClickListener { navigateToTask() }
        cvMember.setOnClickListener { navigateToMember() }
        cvCategory.setOnClickListener { navigateToCategory() }
        dialog.show()
    }

    private fun navigateToTask() {
        val intent = Intent(requireContext(),TaskManagerActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToMember() {
        val intent = Intent(requireContext(),MemberManagerActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToCategory() {
        val intent = Intent(requireContext(),CategoryManagerActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar referencias y recursos del binding
        _binding = null
    }
}
