package com.example.centralecookingclub.ui.detailledRecipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailledRecipeViewModel(application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>().apply {
        value = ""
    }
    val nbPersonne = MutableLiveData<String>().apply {
        value = ""
    }
    val recipe = MutableLiveData<Recipe>().apply {
        value=null
    }
    val ingredientsOfTheRecipe = MutableLiveData<MutableList<Ingredient>>().apply {
        value= mutableListOf()
    }
    val stepsOfTheRecipe = MutableLiveData<MutableList<Step>>().apply {
        value= mutableListOf()
    }
    val quantityOfRecipe = MutableLiveData<MutableList<RecipeQuantity>>().apply {
        value = mutableListOf()
    }
    private val cccRepository by lazy { CCCRepository.newInstance(application)}
    suspend fun getRecipe(id : Int){
        try {
            val temp = cccRepository.localDataSource.getRecipe(id)
            val tempIng = cccRepository.localDataSource.getIngredientsFromRecipe(id)
            val tempStep = cccRepository.localDataSource.getStepsFromRecipe(id)
            val tempQuantity = cccRepository.localDataSource.getRecipeQuantity(id)
            withContext(Main)
            {
                recipe.value = temp
                title.value= recipe.value!!.name
                nbPersonne.value = recipe.value!!.numberOfPeople.toString()
                ingredientsOfTheRecipe.value=tempIng
                stepsOfTheRecipe.value=tempStep
                quantityOfRecipe.value = tempQuantity
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }
    suspend fun updateQuantity(nb : Int)
    {
        withContext(Main)
        {
            quantityOfRecipe.value=cccRepository.localDataSource.scaleQuantitiesOfRecipe(recipe.value!!,nb)
        }
    }

    suspend fun changeFaved(recipe: Recipe) {
        cccRepository.localDataSource.changeFaved(recipe)
        recipe.faved = 1-recipe.faved
        Log.i("Test", recipe.faved.toString())
    }
}