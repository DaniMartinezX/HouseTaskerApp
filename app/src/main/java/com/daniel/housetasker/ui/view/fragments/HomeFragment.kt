package com.daniel.housetasker.ui.view.fragments

import android.app.Dialog
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
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
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
                adapter.updateList(assignmentList)
            }
        }

        adapter = AssignmentAdapter(assignmentList,
            onTaskSelected = {position -> onTaskSelected(position)},
            onDeleteClicked = {position -> onDeleteClicked(position)},
            onStatusClicked = {position -> onStatusClicked(position)},
        )
        binding.rvAssignments.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
        binding.rvAssignments.adapter = adapter

        //Cargando listas para la creación de assignments
        memberViewModel.getAllMembers()
        memberViewModel.memberDataModel.observe(viewLifecycleOwner){members ->
            members?.let {
                memberList = it
            }
        }
        taskViewModel.getAllTask()
        taskViewModel.taskDataModel.observe(viewLifecycleOwner){
            it?.let {
                taskList = it
            }
        }


        binding.btnCreateAssignment.setOnClickListener { showDialogAddAssignment() }
    }

    private fun showDialogAddAssignment() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_assignment)

        //Para la creación de assignments
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
        
        btnCreate.setOnClickListener {
            //Task
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    selectedTaskEntity = taskList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }

            }
            //Miembro
            spinnerMiembros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedMemberEntity = memberList[position] // Obtener la entidad seleccionada del listado
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    return
                }
            }
            //Mapeado: id(task), expirationdate(task), status(task), id(member)

            val idTask = selectedTaskEntity?.id
            val assignmentDate = selectedTaskEntity?.expirationDate
            val statusTask = selectedTaskEntity?.completed
            val idMember = selectedMemberEntity?.id

            Log.i("daniel", idTask.toString())

            val assignment = AssignmentEntity(
                taskId = idTask,
                assignmentDate = assignmentDate.let { 0 },
                completed = statusTask.let { false },
                memberId = idMember.let { 0 }
            )
            assignmentViewModel.insertAssignment(assignment)
            assignmentList = assignmentList + assignment
            adapter.updateList(assignmentList)
            Toast.makeText(requireContext(), "Assignment created", Toast.LENGTH_SHORT).show()
            Log.i("daniel", assignment.toString())
            //TODO no le llega datos
        }

        dialog.show()
    }

    private fun onStatusClicked(position: Int) {

    }

    private fun onDeleteClicked(position: Int) {
        TODO("Not yet implemented")
    }

    private fun onTaskSelected(position: Int) {
        TODO("Not yet implemented")
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
