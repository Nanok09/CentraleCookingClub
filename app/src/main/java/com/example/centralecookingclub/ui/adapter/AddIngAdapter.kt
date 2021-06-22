package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe

class AddIngAdapter( _listAddIng : List<Ingredient>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var listAddIng = _listAddIng
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_ingredient,parent,false)
        return AddIngViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAddIng.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is AddIngViewHolder ->{
                holder.bind(listAddIng.get(position))
            }
        }
    }

    inner class AddIngViewHolder constructor(addIng : View): RecyclerView.ViewHolder(addIng){
        private val titletextView : TextView = addIng.findViewById(R.id.titleAddIng)
        private val quantityTextView : TextView = addIng.findViewById(R.id.quantityAddIng)
/*
        init {
            itemView.setOnClickListener{
                val itemPosition = bindingAdapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = listAddIng[itemPosition]
                    dialog.onItemClickedOnDialog(itemPosition)
                }
            }
        }*/
        fun bind(addIng: Ingredient){
            titletextView.text = addIng.name
        }
    }
}