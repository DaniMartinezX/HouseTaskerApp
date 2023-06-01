package com.daniel.housetasker.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.ui.view.viewholders.AssignmentViewHolder

class AssignmentAdapter(
    private var assignmentList: List<AssignmentEntity>,
    private val onTaskSelected: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
    private val onStatusClicked: (Int) -> Unit,
): RecyclerView.Adapter<AssignmentViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<AssignmentEntity>) {
        assignmentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_assignment, parent, false)
        return AssignmentViewHolder(view, context)
    }

    override fun getItemCount(): Int = assignmentList.size


    override fun onBindViewHolder(viewholder: AssignmentViewHolder, position: Int) {
        viewholder.bind(assignmentList[position])

        val status = viewholder.itemView.findViewById<LinearLayout>(R.id.colorTaskStatus)
        status.setOnClickListener { onStatusClicked(position) }
        viewholder.itemView.setOnClickListener { onTaskSelected(position) }

        val ibDelete = viewholder.itemView.findViewById<ImageButton>(R.id.ibDelete)
        ibDelete.setOnClickListener {
            onDeleteClicked(position)
        }
    }


}