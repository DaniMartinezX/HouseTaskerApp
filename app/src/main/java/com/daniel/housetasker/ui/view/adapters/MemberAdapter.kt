package com.daniel.housetasker.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.ui.view.viewholders.MemberViewHolder

class MemberAdapter(private var membersList: List<MemberEntity> = emptyList()) : RecyclerView.Adapter<MemberViewHolder>() {

    interface  OnItemClickListener{
        fun onItemClick(member: MemberEntity)
    }

    //todo functions lambda

    private var selectedItemIndex = -1
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

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
    }
}