package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.model.Step
import kotlin.math.floor

class IngAndStepRecyclerAdapter(val actionListener: ActionListener, _ingAndStep : List<Any>,quantityRecipe : MutableList<RecipeQuantity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var ingsAndSteps : List<Any> = _ingAndStep
    var quantity : MutableList<RecipeQuantity> = quantityRecipe

    override fun getItemViewType(position: Int): Int {
        if(ingsAndSteps[position] is Ingredient) return 0
        else return 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if(type==0)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient,parent,false)
            return IngredientViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.step,parent,false)
            return StepViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return ingsAndSteps.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is IngredientViewHolder ->{
                if(ingsAndSteps.get(position) is Ingredient)
                {
                    holder.bind(ingsAndSteps[position] as Ingredient)
                }
            }
            is StepViewHolder->{
                if(ingsAndSteps.get(position)is Step)
                {
                    holder.bind(ingsAndSteps[position] as Step)
                }
            }
        }
    }

    inner class IngredientViewHolder constructor(ingredient : View): RecyclerView.ViewHolder(ingredient){
        private val nameTextView : TextView = ingredient.findViewById(R.id.ingredientName)
        private val quantityTextView : TextView = ingredient.findViewById(R.id.quantityIngredient)
        private val unitTextView : TextView = ingredient.findViewById(R.id.unitIngredient)

        fun bind(ingredient: Ingredient){
            quantity.forEach {
                if (it.idIngredient==ingredient.id)
                {
                    if(floor(it.quantity)==it.quantity)
                    {
                        val nb = it.quantity.toInt()
                        quantityTextView.text=nb.toString()
                    }
                    else
                    {
                        quantityTextView.text= String.format("%.1f",it.quantity)
                    }
                }
            }
            nameTextView.text = ingredient.name
            unitTextView.text=ingredient.unit
        }
    }
    inner class StepViewHolder constructor(step : View): RecyclerView.ViewHolder(step){
        private val numEtapeTextView : TextView = step.findViewById(R.id.stepTV)
        private val descriptionTextView : TextView = step.findViewById(R.id.descriptionStep)

        fun bind(step: Step){
            descriptionTextView.text = step.description
            numEtapeTextView.text = step.stepNumber.toString()
        }
    }

    interface ActionListener {
        fun onItemClicked(position: Int)
    }
}