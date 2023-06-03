package com.daniel.housetasker.ui.view.viewholders

import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.AssignmentEntity
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.data.database.entities.TaskEntity
import com.daniel.housetasker.databinding.ItemAssignmentBinding
import com.daniel.housetasker.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AssignmentViewHolder (view: View, private val context: Context) : RecyclerView.ViewHolder(view){

    private val binding = ItemAssignmentBinding.bind(view)

    fun bind(assignmentEntityResponse: AssignmentEntity, memberEntity: MemberEntity?,taskEntity: TaskEntity?){
        val green = ContextCompat.getColor(context, R.color.green)
        val red = ContextCompat.getColor(context, R.color.red)

        val expirationDate = taskEntity?.expirationDate ?: 0L
        val formattedDate = formatDate(expirationDate)

        binding.tvNameTask.text = taskEntity?.name
        binding.tvAssignmentDay.text = formattedDate
        binding.tvMemberName.text = memberEntity?.name

        if (taskEntity?.completed == false){
            binding.colorTaskStatus.setBackgroundColor(red)
            binding.tvStatus.text = "Uncompleted"
        } else {
            binding.colorTaskStatus.setBackgroundColor(green)
            binding.tvStatus.text = "Completed"
        }

        Log.i("Estado",taskEntity?.completed.toString())
    }

    private fun formatDate(dateInMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(dateInMillis)
        return sdf.format(date)
    }
}