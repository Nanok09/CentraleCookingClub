package com.example.centralecookingclub.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.ShoppingListItem

class ShoppingListItemAdapter(private val shoppingList: List<ShoppingListItem>, private val actionListener: ActionListener)
    : RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListItemViewHolder {
        val listeToDoView = LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, parent, false)
        return ShoppingListItemViewHolder(listeToDoView)
    }

    override fun onBindViewHolder(holder: ShoppingListItemViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        holder.checkBoxView.isChecked = currentItem.bought == 1
        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = shoppingList.size

    inner class ShoppingListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val checkBoxView: CheckBox = itemView.findViewById(R.id.boughtCheckBox)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val textView: TextView = itemView.findViewById(R.id.shoppingListItemName)

        init {
            itemView.setOnClickListener(this)
            checkBoxView.setOnClickListener(this)
            deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Log.i("Test", "Test")
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                actionListener.onItemClick(v!!, position)
            }
        }
    }

    interface ActionListener {
        fun onItemClick(view: View, position: Int)
    }
}