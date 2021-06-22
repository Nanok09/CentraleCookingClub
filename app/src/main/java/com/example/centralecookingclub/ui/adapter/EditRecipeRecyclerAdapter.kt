package com.example.centralecookingclub.ui.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Ingredient

class EditRecipeRecyclerAdapter(val actionListener: ActionListener, _editRecipeList : List<EditRecipe>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var editRecipeList : List<EditRecipe>
    init {
         editRecipeList = _editRecipeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if(type==0)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_recipe,parent,false)
            return EditRecipeViewHolder(view)
        }
        if(type==1)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.image_of_recipe,parent,false)
            return ImageRecipeViewHolder(view)
        }
        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_ingredient,parent,false)
            return EditIngViewHolder(view)
        }

    }
    override fun getItemViewType(position: Int): Int {
        if(editRecipeList[position].type==0) return 0
        if(editRecipeList[position].type==1) return 1
        else return 2
    }

    override fun getItemCount(): Int {
        return editRecipeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is EditRecipeViewHolder ->{
                holder.bind(editRecipeList.get(position),position)
            }
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class EditRecipeViewHolder constructor(editRecipe : View): RecyclerView.ViewHolder(editRecipe){
        private val numOfStepTV : TextView = editRecipe.findViewById(R.id.numEtape)

        fun bind(editRecipe: EditRecipe,position: Int){
            numOfStepTV.text= (position+1).toString()
        }
    }
    inner class EditIngViewHolder constructor(editIng : View): RecyclerView.ViewHolder(editIng){
        fun bind(editIng: EditRecipe,position: Int){

        }
    }
    inner class ImageRecipeViewHolder constructor(image : View): RecyclerView.ViewHolder(image){

        init {
            itemView.setOnClickListener{
                val itemPosition = bindingAdapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = editRecipeList[itemPosition]
                    val imageview = image.findViewById<ImageView>(R.id.imageofRecipe)
                    actionListener.onItemClicked(itemPosition,imageview)
                }
            }
        }
    }
    interface ActionListener {

        fun onItemClicked(position: Int,stepImage :ImageView)
    }
}