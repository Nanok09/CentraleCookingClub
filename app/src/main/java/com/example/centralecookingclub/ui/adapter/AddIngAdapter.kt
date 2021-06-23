package com.example.centralecookingclub.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.Ingredient
import java.lang.Exception


class AddIngAdapter( _listAddIng : List<Ingredient>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var arrQuantity = FloatArray(_listAddIng.size){0f}
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
                holder.bind(listAddIng.get(position),position)
            }
        }
    }

    inner class AddIngViewHolder constructor(addIng : View): RecyclerView.ViewHolder(addIng){
        private val titletextView : TextView = addIng.findViewById(R.id.titleAddIng)
        private val quantityEditText : EditText = addIng.findViewById(R.id.quantityAddIng)
        private val unitTextView : TextView = addIng.findViewById(R.id.addIngUnit)
        lateinit var myCustomEditTextListener : MyCustomEditTextListener

        init {
            myCustomEditTextListener=MyCustomEditTextListener()
            quantityEditText.addTextChangedListener(myCustomEditTextListener)
        }
        fun bind(addIng: Ingredient,position: Int){
            titletextView.text = addIng.name
            unitTextView.text=addIng.unit
            myCustomEditTextListener.updatePosition(bindingAdapterPosition)
            quantityEditText.setText(arrQuantity[position].toString())
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
                arrQuantity[position]=temp.toString().toFloat()
            }
            catch (e:Exception)
            {
                Log.d("CCC",e.toString())
            }
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }
}