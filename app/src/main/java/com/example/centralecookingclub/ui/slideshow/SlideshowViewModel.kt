package com.example.centralecookingclub.ui.slideshow

import android.app.Application
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import kotlinx.coroutines.launch
import java.lang.Exception

class SlideshowViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    private val cccRepository by lazy { CCCRepository.newInstance(application)}


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
    }
}