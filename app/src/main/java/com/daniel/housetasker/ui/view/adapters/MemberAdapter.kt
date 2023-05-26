package com.daniel.housetasker.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.ui.view.viewholders.MemberViewHolder

class MemberAdapter(private var membersList: List<MemberEntity> , private val onMemberSelected: (Int) -> Unit, private val onDeleteClicked: (Int) -> Unit) : RecyclerView.Adapter<MemberViewHolder>() {



    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MemberEntity>) {
        membersList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        )
    }

    override fun getItemCount(): Int = membersList.size

    override fun onBindViewHolder(viewholder: MemberViewHolder, position: Int) {
        viewholder.bind(membersList[position])
        viewholder.itemView.setOnClickListener { onMemberSelected(position) }

        //Para el delete
        val ibDelete = viewholder.itemView.findViewById<ImageButton>(R.id.ibDelete)
        ibDelete.setOnClickListener {
        //Utilizo la variable position para acceder a los datos del elemento en concreto
        onDeleteClicked(position)
        }
    }
}