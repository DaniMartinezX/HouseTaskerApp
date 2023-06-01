package com.daniel.housetasker.ui.view.viewholders

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.databinding.ItemAssignmentBinding
import okhttp3.Response

class AssignmentViewHolder (view: View, private val context: Context) : RecyclerView.ViewHolder(view){

    private val binding = ItemAssignmentBinding.bind(view)

    fun bind(assignmentEntityResponse: AssignmentEntity){
        val green = ContextCompat.getColor(context, R.color.green)
        val red = ContextCompat.getColor(context, R.color.red)

        //binding.tvNameTask.text = nombre del TASK (addAssignment)
        //binding.tvAssignmentDay.text = data de finalizacion del TASK
        //binding.tvMemberName.text = nombre del miembro assignado (addAssignment)
        /*
        if (status del task){
            binding.colorTaskStatus.setBackgroundColor(red)
        } else {
            binding.colorTaskStatus.setBackgroundColor(green)
        }
         */

    }
}