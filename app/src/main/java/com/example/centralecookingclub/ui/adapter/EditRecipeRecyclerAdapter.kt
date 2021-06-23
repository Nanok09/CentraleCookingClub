package com.example.centralecookingclub.ui.adapter

import android.media.Image
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Ingredient
import java.lang.Exception
import kotlin.math.floor

class EditRecipeRecyclerAdapter(val actionListener: ActionListener, _editRecipeList : List<EditRecipe>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var editRecipeList : List<EditRecipe>
    lateinit var descriptionList : MutableList<String>
    lateinit var quantityList : MutableList<String>
    init {
         editRecipeList = _editRecipeList
        descriptionList = mutableListOf()
        quantityList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if(type==0)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_recipe,parent,false)
            return EditRecipeViewHolder(view)
        }
        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient,parent,false)
            return EditIngViewHolder(view)
        }

    }
    override fun getItemViewType(position: Int): Int {
        if(editRecipeList[position].type==0) return 0
        else return 1
    }

    override fun getItemCount(): Int {
        return editRecipeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is EditRecipeViewHolder ->{
                holder.bind(position)
            }
            is EditIngViewHolder ->{
                holder.bind(editRecipeList.get(position).ingredient,position)
            }
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class EditRecipeViewHolder constructor(editRecipe : View): RecyclerView.ViewHolder(editRecipe){
        private val numOfStepTV : TextView = editRecipe.findViewById(R.id.numEtape)
        private val descriptionEditText : EditText = editRecipe.findViewById(R.id.description)
        lateinit var myCustomEditTextListener : MyCustomEditTextListener
        init {
            myCustomEditTextListener=MyCustomEditTextListener()
            descriptionEditText.addTextChangedListener(myCustomEditTextListener)
        }

        fun bind(position: Int){
            numOfStepTV.text= (position+1-quantityList.size).toString()
            myCustomEditTextListener.updatePosition(bindingAdapterPosition)
            descriptionEditText.setText(descriptionList[bindingAdapterPosition-quantityList.size])
        }
    }
    inner class EditIngViewHolder constructor(editIng : View): RecyclerView.ViewHolder(editIng){
        private val ingredientNametextView : TextView = editIng.findViewById(R.id.ingredientName)
        private val quantityIngredienttextView : TextView = editIng.findViewById(R.id.quantityIngredient)
        private val unitTextView : TextView = editIng.findViewById(R.id.unitIngredient)
        fun bind(editIng: Ingredient,position: Int){
            val num = quantityList[position].toFloat()
            if(floor(num)==num)
            {
                quantityIngredienttextView.text=num.toInt().toString()
            }
            else
            {
                quantityIngredienttextView.text=num.toString()
            }
            ingredientNametextView.text=editIng.name

            unitTextView.text=editIng.unit
        }
    }
    inner class MyCustomEditTextListener : TextWatcher {
        private var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            val temp = charSequence
            try {
                descriptionList[position-quantityList.size]=temp.toString()
            }
            catch (e: Exception)
            {
                Log.d("CCC",e.toString())
            }
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }
    interface ActionListener {

        fun onItemClicked(position: Int,stepImage :ImageView)
    }
}