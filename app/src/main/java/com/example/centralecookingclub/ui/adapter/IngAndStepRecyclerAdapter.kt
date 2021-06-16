package com.example.centralecookingclub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Step

class IngAndStepRecyclerAdapter(val actionListener: ActionListener, _ingAndStep : List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var ingsAndSteps : List<Any> = _ingAndStep

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

        fun bind(ingredient: Ingredient){
            nameTextView.text = ingredient.name
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