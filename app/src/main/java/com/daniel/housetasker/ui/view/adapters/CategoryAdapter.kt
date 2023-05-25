package com.daniel.housetasker.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.daniel.housetasker.R
import com.daniel.housetasker.data.database.entities.CategoryEntity
import com.daniel.housetasker.ui.view.viewholders.CategoryViewHolder

class CategoryAdapter(private var categoriesList: List<CategoryEntity>,private var colorHex: String,private val onCategorySelected: (Int) -> Unit, private val onDeleteClicked: (Int) -> Unit) : RecyclerView.Adapter<CategoryViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<CategoryEntity>) {
        categoriesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, colorHex)
    }

    override fun getItemCount(): Int = categoriesList.size

    override fun onBindViewHolder(viewholder: CategoryViewHolder, position: Int) {
        viewholder.bind(categoriesList[position])
        viewholder.itemView.setOnClickListener { onCategorySelected(position) }

        val ibDelete = viewholder.itemView.findViewById<ImageButton>(R.id.ibDelete)
        ibDelete.setOnClickListener {
            //Utilizo la variable position para acceder a los datos del elemento en concreto
            onDeleteClicked(position)
        }
    }


}