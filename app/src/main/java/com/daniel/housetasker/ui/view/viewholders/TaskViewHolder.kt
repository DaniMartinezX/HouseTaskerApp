package com.daniel.housetasker.ui.view.viewholders

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.databinding.ItemTaskBinding
import java.util.Date


class TaskViewHolder (view: View, private val context: Context) : RecyclerView.ViewHolder(view)  {

    private val binding = ItemTaskBinding.bind(view)


    fun bind(taskEntityResponse: TaskEntity){
        val green = ContextCompat.getColor(context, R.color.green)
        val red = ContextCompat.getColor(context, R.color.red)

        binding.tvNameTask.text = taskEntityResponse.name
        val fechaCreacion = Date(taskEntityResponse.creationDate)
        binding.tvCreationDate.text = fechaCreacion.toString()
        val fechaFinalizacion = Date(taskEntityResponse.expirationDate)
        binding.tvExpirationDate.text = fechaFinalizacion.toString()
        if (!taskEntityResponse.completed){
            binding.tvCompleted.text = "Uncompleted"
            binding.colorCompleted.setBackgroundColor(red)
        } else {
            binding.tvCompleted.text = "Completed"
            binding.colorCompleted.setBackgroundColor(green)
        }
    }
}