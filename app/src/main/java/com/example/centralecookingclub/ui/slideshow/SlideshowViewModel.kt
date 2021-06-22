package com.example.centralecookingclub.ui.slideshow

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.ShoppingListItem
import com.example.centralecookingclub.ui.gallery.GalleryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SlideshowViewModel(application: Application) : AndroidViewModel(application) {

    var shoppingList = MutableLiveData<MutableList<ShoppingListItem>>()
    private val cccRepository by lazy { CCCRepository.newInstance(application)}




    /*private val _text = MutableLiveData<String>().apply {
         value = "This is slideshow Fragment"
     }
     val text: LiveData<String> = _text*/

    /*// En vrai, on devrait pas mettre ça dans slideshowViewModel(ça a pas grand chose à voir avec le slideshow)
    // Permet l'ajout des ingrédients dans la DB
    val ingredients = MutableLiveData<ViewState>()


    fun addIngredient(ingredient: Ingredient){
        viewModelScope.launch {
            try {
                cccRepository.localDataSource.addIngredient(ingredient)
            } catch (e: Exception){
                ingredients.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    // Permet l'ajout d'une recette dans la DB
    val recipes = MutableLiveData<ViewState>()
    fun addRecipe(recipe: Recipe){
        viewModelScope.launch {
            try {
                cccRepository.localDataSource.addRecipe(recipe)
            } catch (e: Exception){
                recipes.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    // Permet l'ajout des étapes dans la DB
    // Permet l'ajout des quantités dans la DB


    // Permet d'afficher tous les ingrédients de la DB
    fun loadIngredients(){

        viewModelScope.launch {
            ingredients.value = ViewState.Loading
            try {
                ingredients.value = ViewState.Content(ingredients = cccRepository.localDataSource.getAllIngredients())
            } catch (e: Exception){
                ingredients.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    sealed class ViewState{
        object Loading : ViewState()
        data class Content(val ingredients: MutableList<Ingredient>) : ViewState()
        data class Error(val message: String) : ViewState()
    }*/










    suspend fun getShoppingListItems(){
        try {
            val items = cccRepository.localDataSource.getAllShoppingListItems()
            withContext(Dispatchers.Main)
            {
                shoppingList.value = items
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }

    suspend fun addItem(item: ShoppingListItem) {
        //Pour updater l'observer
        val temp = mutableListOf<ShoppingListItem>()
        temp.addAll(shoppingList.value!!)
        temp.add(item)
        withContext(Main) {
            shoppingList.value = temp
        }

        //BDD
        try {
            cccRepository.localDataSource.addShoppingListItem(item)
        } catch (e: Exception) {
            Log.d("CCC", e.toString())
        }
    }

    suspend fun deleteItem(item: ShoppingListItem) {
        //Pour updater l'observer
        val temp = mutableListOf<ShoppingListItem>()
        temp.addAll(shoppingList.value!!)
        temp.remove(item)
        withContext(Main) {
            shoppingList.value = temp
        }

        //BDD
        try {
            cccRepository.localDataSource.deleteShoppingListItem(item)
        } catch (e: Exception) {
            Log.d("CCC", e.toString())
        }
    }

    suspend fun getLastIdItem(): Int {
        return cccRepository.localDataSource.getLastIdItem()
    }

    suspend fun changeBought(item: ShoppingListItem) {
        cccRepository.localDataSource.changeBought(item)
        item.bought = 1 - item.bought
    }
}
