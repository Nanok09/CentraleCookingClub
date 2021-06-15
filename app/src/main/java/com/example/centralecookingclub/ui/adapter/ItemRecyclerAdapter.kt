package com.example.centralecookingclub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.Recette

class ItemRecyclerAdapter( _recettes : List<Recette>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var recettes : List<Recette> = _recettes
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
        private val decriptiontextView : TextView = recette.findViewById(R.id.descriptionOfItem)

        fun bind(recette: Recette){
            titletextView.text = recette.nom
            decriptiontextView.text = recette.description
        }
    }
    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}