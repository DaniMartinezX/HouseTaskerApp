package com.daniel.housetasker.ui.view.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.daniel.housetasker.R
import com.daniel.housetasker.databinding.FragmentHomeBinding
import com.daniel.housetasker.ui.view.activities.AssignmentManagerActivity
import com.daniel.housetasker.ui.view.activities.CategoryManagerActivity
import com.daniel.housetasker.ui.view.activities.MemberManagerActivity
import com.daniel.housetasker.ui.view.activities.TaskManagerActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add)

        val cvTask : CardView = dialog.findViewById(R.id.cvTask)
        val cvMember : CardView = dialog.findViewById(R.id.cvMember)
        val cvCategory : CardView = dialog.findViewById(R.id.cvCategory)
        val cvAssignment : CardView = dialog.findViewById(R.id.cvAssignment)

        cvTask.setOnClickListener { navigateToTask() }
        cvMember.setOnClickListener { navigateToMember() }
        cvCategory.setOnClickListener { navigateToCategory() }
        cvAssignment.setOnClickListener { navigateToAssignment() }
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
    private fun navigateToAssignment() {
        val intent = Intent(requireContext(),AssignmentManagerActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar referencias y recursos del binding
        _binding = null
    }
}
