package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Recipe

class ItemRecyclerAdapter(val actionListener: ActionListener, _recettes : List<Recipe>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var recettes : List<Recipe> = _recettes
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recette,parent,false)
        return RecetteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recettes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is RecetteViewHolder ->{
                holder.bind(recettes.get(position))
            }
        }
    }

    inner class RecetteViewHolder constructor(recette : View): RecyclerView.ViewHolder(recette){
        private val titletextView : TextView = recette.findViewById<TextView>(R.id.titreItem)
        init {
            itemView.setOnClickListener{
                val itemPosition = bindingAdapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = recettes[itemPosition]
                    actionListener.onItemClicked(itemPosition)
                }
            }
        }
        fun bind(recette: Recipe){
            Log.d("CCC",recette.name)
            titletextView.text = recette.name
        }
    }
    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}