package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
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
        private val titletextView : TextView = recette.findViewById(R.id.titreItem)
        private val timeTextView : TextView = recette.findViewById(R.id.time)
        private val nbPersonnesTextView : TextView = recette.findViewById(R.id.nombrePersonnes)
        private val recipeImg: ImageView = recette.findViewById(R.id.imageRecipe)
        private val ic_favori:CheckBox = recette.findViewById((R.id.ic_favori))

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
            titletextView.text = recette.name+" by "+recette.authorRecipe
            timeTextView.text = recette.time.toString()
            nbPersonnesTextView.text=recette.numberOfPeople.toString()
            recipeImg.setImageBitmap(recette.recipeImage)
            ic_favori.isChecked = recette.faved==1
        }
    }
    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}