package com.daniel.housetasker.ui.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.data.database.entities.MemberEntity
import com.daniel.housetasker.databinding.ItemMemberBinding
import com.squareup.picasso.Picasso

class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMemberBinding.bind(view)

    fun bind(memberEntityResponse: MemberEntity){
        binding.tvName.text = memberEntityResponse.name
        binding.tvId.text = memberEntityResponse.id.toString()
        Picasso.get().load(memberEntityResponse.photo).resize(40,40).into(binding.ivPhoto)
    }
}