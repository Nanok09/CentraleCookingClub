package com.example.centralecookingclub.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centralecookingclub.R
import com.example.centralecookingclub.data.model.ShoppingListItem
import com.example.centralecookingclub.databinding.FragmentSlideshowBinding
import com.example.centralecookingclub.ui.adapter.ShoppingListItemAdapter
import kotlinx.coroutines.*
import java.lang.Runnable


class SlideshowFragment() : Fragment(), ShoppingListItemAdapter.ActionListener,
    View.OnClickListener {

    private val activityScope = CoroutineScope(Dispatchers.IO)
    private val CAT = "EDPMR"
    //private var btnCreateToast : Button? = null

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    private lateinit var addButton: Button
    private lateinit var newItemName: EditText
    private lateinit var shoppingListAdapter: ShoppingListItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var _shoppingList: MutableList<ShoppingListItem>
    private val fragmentScope = CoroutineScope(Dispatchers.IO)


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

        addButton = binding.addButton
        addButton.setOnClickListener(this)
        newItemName = binding.newItemNameEditText

        //
        _shoppingList = mutableListOf(ShoppingListItem(-1, "Chargement des données", 0))
        fragmentScope.launch {
            slideshowViewModel.getShoppingListItems()
        }

        recyclerView = binding.shoppingListItemRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        shoppingListAdapter = ShoppingListItemAdapter(_shoppingList, this)
        recyclerView.adapter = shoppingListAdapter


//        val textView: TextView = binding.textSlideshow
//        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

//        //Création d'un bouton cliquable.
//        btnCreateToast = root.findViewById(R.id.btnToast)
//        btnCreateToast!!.setOnClickListener(this)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        slideshowViewModel.shoppingList.observe(viewLifecycleOwner, Observer { shoppingList ->
            Log.i("Test", "observing")
            _shoppingList.clear()
            _shoppingList.addAll(shoppingList)
            shoppingListAdapter.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onClick(v: View?) {

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


    }*/


    /*fun addIngredient(ingredient: Ingredient){
        slideshowViewModel.addIngredient(ingredient)
    }

    fun addRecipe(recipe: Recipe) {
        slideshowViewModel.addRecipe(recipe)
    }*/


    //CLICK SUR LA CHECKBOX OU LE DELETE_BUTTON D'UN ITEM DE LA SHOPPING LIST
    override fun onItemClick(view: View, position: Int) {
        Log.i("ShoppingListTest", "un item a été cliqué")
        val item = slideshowViewModel.shoppingList.value!!.get(position)

        fragmentScope.launch {
            when (view.id) {
                R.id.boughtCheckBox -> {
                    Log.i("ShoppingListTest", "bought checkbox")
                    slideshowViewModel.changeBought(item)
                }
                R.id.deleteButton -> {
                    Log.i("ShoppingListTest", "delete button")
                    slideshowViewModel.deleteItem(item)
                }
            }
        }
    }

    //CLICK SUR LE BOUTON POUR AJOUTER UN ITEM
    //AJOUTE SEULEMENT SI CHAMP NON VIDE
    override fun onClick(v: View?) {
        Log.i("ShoppingListTest", "ajouter item à shopping list")
        val name = newItemName.text.toString()

        if (name != "") {
            fragmentScope.launch {
                val idItem = slideshowViewModel.getLastIdItem() + 1
                val bought = 0
                val item = ShoppingListItem(idItem, name, bought)

                slideshowViewModel.addItem(item)
                Log.i("ShoppingListTest", item.toString())
            }
        }
    }

}