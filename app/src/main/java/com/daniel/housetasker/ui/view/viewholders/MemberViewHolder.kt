package com.daniel.housetasker.ui.view.viewholders

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
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
        val decodedBytes = Base64.decode(memberEntityResponse.photo, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
       binding.ivPhoto.setImageBitmap(bitmap)
    }
}