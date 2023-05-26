package com.daniel.housetasker.ui.view.viewholders

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.databinding.ItemCategoryBinding

class CategoryViewHolder(view: View, private val colorHex: String) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCategoryBinding.bind(view)

    fun bind(categoryEntityResponse: CategoryEntity){
        binding.tvName.text = categoryEntityResponse.name
        binding.tvId.text = categoryEntityResponse.id.toString()
        Log.i("dani view holder",categoryEntityResponse.color)

        val colorInt = Color.parseColor(categoryEntityResponse.color)
        binding.ivColor.setBackgroundColor(colorInt)
    }
}