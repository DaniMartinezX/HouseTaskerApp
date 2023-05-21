package com.daniel.housetasker.ui.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.databinding.ItemMemberBinding

class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMemberBinding.bind(view)

    fun bind(memberEntityResponse: MemberEntity){
        binding.tvName.text = memberEntityResponse.name
        binding.tvId.text = memberEntityResponse.id.toString()
        //todo foto
    }
}