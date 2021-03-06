package com.example.centralecookingclub.ui.gallery

import android.app.Application
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.*
import kotlinx.coroutines.launch
import java.lang.Exception

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    var editRecipeList = MutableLiveData<MutableList<EditRecipe>>().apply {
        value = mutableListOf<EditRecipe>()
    }
    val text: LiveData<String> = _text

    private val cccRepository by lazy { CCCRepository.newInstance(application)}

    val steps = MutableLiveData<ViewState>()
    val recipe = MutableLiveData<ViewState>()
    val recipe_quantities = MutableLiveData<ViewState>()

    fun addStep(step: Step){
        viewModelScope.launch {
            try {
                cccRepository.localDataSource.addStep(step)
            } catch (e: Exception){
                steps.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }


    // Permet d'afficher toutes les étapes de la DB
    fun loadSteps(){

        viewModelScope.launch {
            steps.value = ViewState.Loading
            try {
                steps.value = ViewState.Content(steps = cccRepository.localDataSource.getAllSteps())
            } catch (e: Exception){
                steps.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }
    fun addEditRecipe()
    {
        val temp = mutableListOf<EditRecipe>()
        temp.addAll(editRecipeList.value!!)
        temp.add(EditRecipe(0))
        editRecipeList.value=temp
    }
    fun addEditIng(ingredient: Ingredient)
    {
        val temp = mutableListOf<EditRecipe>()
        temp.addAll(editRecipeList.value!!)
        val myNewEditRecipe = EditRecipe(1)
        myNewEditRecipe.ingredient=ingredient
        temp.add(0,myNewEditRecipe)
        editRecipeList.value=temp
    }

    suspend fun saveRecipeToDatabase(recipeToAdd: Recipe){
        viewModelScope.launch {

            recipe.value = ViewState.Loading
            try {
                recipe.value = ViewState.RecipeContent(recipeToAdd)
                cccRepository.localDataSource.addRecipe(recipeToAdd)
            } catch (e: Exception){
                recipe.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }


    suspend fun saveSteps(stepsToAdd: MutableList<Step>){
        viewModelScope.launch {

            steps.value = ViewState.Loading
            try {
                steps.value = ViewState.Content(stepsToAdd)
                stepsToAdd.forEach {
                    cccRepository.localDataSource.addStep(it)
                }
            } catch (e: Exception){
                steps.value = ViewState.Error(e.message.orEmpty())
            }
        }
    }

    suspend fun saveRecipeQuantities(recipQuantitiesToAdd: MutableList<RecipeQuantity>){
        viewModelScope.launch {

            recipe_quantities.value = ViewState.Loading
            try {
                recipe_quantities.value = ViewState.RecipeQuantityContent(recipQuantitiesToAdd)
                recipQuantitiesToAdd.forEach {
                    cccRepository.localDataSource.addRecipeQuantity(it)
                }
            } catch (e: Exception){
                recipe_quantities.value = ViewState.Error(e.message.orEmpty())
            }
        }

    }

    suspend fun getLastId(): Int {
        return cccRepository.localDataSource.getLastId()
    }

    suspend fun initializeListOfIngredients() : List<Ingredient> {
        return cccRepository.localDataSource.getAllIngredients()
    }



    sealed class ViewState{
        object Loading : ViewState()
        data class Content(val steps: MutableList<Step>) : ViewState()
        data class RecipeContent(val recipe: Recipe) : ViewState()
        data class RecipeQuantityContent(val recipe_quantities: MutableList<RecipeQuantity>): ViewState()
        data class Error(val message: String) : ViewState()
    }



}