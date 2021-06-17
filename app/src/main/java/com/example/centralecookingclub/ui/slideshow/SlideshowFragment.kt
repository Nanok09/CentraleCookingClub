package com.example.centralecookingclub.ui.slideshow

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.centralecookingclub.MainActivity
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.databinding.FragmentSlideshowBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class SlideshowFragment() : Fragment(), View.OnClickListener {

    private val activityScope = CoroutineScope(Dispatchers.IO)
    private val CAT = "EDPMR"
    private var btnCreateToast : Button? = null

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //Création d'un bouton cliquable.
        btnCreateToast = root.findViewById(R.id.btnToast)
        btnCreateToast!!.setOnClickListener(this)

//        //TEST POUR UNE RECETTE DE CRÊPES
//        //REMPLISSAGE DE LA DATABASE INGREDIENTS
//        // L'idée après c'est de mettre la création de la DB dans une activité tierce
//        val egg = Ingredient (1, "egg")
//        val milk = Ingredient(2, "milk")
//        val flour = Ingredient(3, "flour")
//        val salt = Ingredient(4, "salt")
//        val sugar = Ingredient(5, "sugar")
//        val butter = Ingredient(6, "butter")
//        addIngredient(egg)
//        addIngredient(milk)
//        addIngredient(flour)
//        addIngredient(salt)
//        addIngredient(sugar)
//        addIngredient(butter)
//
//        //REMPLISSAGE DE LA DATA BASE RECETTES
//        val crepes = Recipe(1,"crepes", 25, "test", 4)
//        addRecipe(crepes)
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {

//        activityScope.launch {
//            val ingredients = cccRepository.localDataSource.getAllIngredients()
//
//            withContext(Main){
//                Log.i(CAT, ingredients[0].name)
//                val t = Toast.makeText(activity, ingredients[0].name, Toast.LENGTH_SHORT)
//                t.show()
//            }
//        }



        slideshowViewModel.loadIngredients()
        slideshowViewModel.ingredients.observe(this) {
            viewState ->
            when(viewState){
                is SlideshowViewModel.ViewState.Content -> {
                    viewState.ingredients.forEach {
                        Log.i(CAT, it.name)
                        Log.i(CAT, it.id.toString())
                        val t = Toast.makeText(activity, it.name, Toast.LENGTH_SHORT)
                        t.show()
                    }
                }
                is SlideshowViewModel.ViewState.Error -> {
                    Toast.makeText(activity, "${viewState.message} ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }


    fun addIngredient(ingredient: Ingredient){
        slideshowViewModel.addIngredient(ingredient)
    }

    fun addRecipe(recipe: Recipe) {
        slideshowViewModel.addRecipe(recipe)
    }
}