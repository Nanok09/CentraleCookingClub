package com.example.centralecookingclub.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    var recettes = MutableLiveData<MutableList<Recipe>>()
    private val cccRepository by lazy { CCCRepository.newInstance(application)}
    val text: LiveData<String> = _text

    suspend fun getRecipes(){
        viewModelScope.launch {
            try {
                recettes.value = cccRepository.localDataSource.getAllRecipes()
            } catch (e: Exception){
                Log.d("CCC",e.toString())
            }
        }
    }
}