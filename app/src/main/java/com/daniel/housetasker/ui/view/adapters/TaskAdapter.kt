package com.daniel.housetasker.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.ui.view.viewholders.TaskViewHolder

class TaskAdapter (private var taskList: List<TaskEntity>, private val onTaskSelected: (Int) -> Unit, private val onDeleteClicked: (Int) -> Unit) : RecyclerView.Adapter<TaskViewHolder>(){

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<TaskEntity>) {
        taskList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view,context)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(viewholder: TaskViewHolder, position: Int) {
        viewholder.bind(taskList[position])
        viewholder.itemView.setOnClickListener { onTaskSelected(position) }
        //Para el delete
        val ibDelete = viewholder.itemView.findViewById<ImageButton>(R.id.ibDelete)
        ibDelete.setOnClickListener {
            //Utilizo la variable position para acceder a los datos del elemento en concreto
            onDeleteClicked(position)
        }
    }

}