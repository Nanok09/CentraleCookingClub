package com.example.centralecookingclub.ui.home

import android.R
import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Switch
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    var recettes = MutableLiveData<MutableList<Recipe>>()
    private val cccRepository by lazy { CCCRepository.newInstance(application)}

    val text: LiveData<String> = _text

    suspend fun getRecipes(){
        try {
            val recipe = cccRepository.localDataSource.getAllRecipes()
            withContext(Main)
            {
                recettes.value =recipe
                Log.d("CCC", recettes.value!![0].name)
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }

    suspend fun getRecipesByName(name:String){
        try {
            val recipe = cccRepository.localDataSource.getAllRecipesByName(name)
            withContext(Main)
            {
                recettes.value =recipe
                Log.d("CCC", recettes.value!![0].name)
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }



    suspend fun setAutocompleteTextView(acResearch: AutoCompleteTextView){
        try {
            val recipeNames = cccRepository.localDataSource.getAllRecipeNames()
            withContext(Main)
            {

                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    getApplication(),
                    R.layout.simple_dropdown_item_1line, recipeNames
                )
                acResearch.setAdapter(adapter)
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }

    suspend fun research(name: String, swFaved: Switch) {
        try {
            var recipe = cccRepository.localDataSource.getAllRecipesByName(name)
            val isChecked = swFaved.isChecked
            withContext(Main)
            {   if (isChecked == true) recipe = cccRepository.localDataSource.researchInFavorites(name)
                recettes.value = recipe
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }
}