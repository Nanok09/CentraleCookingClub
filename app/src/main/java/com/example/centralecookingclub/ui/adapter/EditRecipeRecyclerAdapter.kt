package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.EditRecipe

class EditRecipeRecyclerAdapter(val actionListener: ActionListener, _editRecipeList : List<EditRecipe>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var editRecipeList : List<EditRecipe>
    init {
         editRecipeList = _editRecipeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_recipe,parent,false)
        return EditRecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return editRecipeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is EditRecipeViewHolder ->{
                holder.bind(editRecipeList.get(position))
            }
        }
    }

    inner class EditRecipeViewHolder constructor(editRecipe : View): RecyclerView.ViewHolder(editRecipe){
        private val descriptionTV : TextView = editRecipe.findViewById(R.id.description)
        private val numOfStepTV : TextView = editRecipe.findViewById(R.id.numEtape)
        /*
        init {
            itemView.setOnClickListener{
                val itemPosition = bindingAdapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = editRecipeList[itemPosition]
                    actionListener.onItemClicked(itemPosition)
                }
            }
        }
        */
        fun bind(editRecipe: EditRecipe){
            //descriptionTV.text=editRecipe.description
        }
    }
    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}