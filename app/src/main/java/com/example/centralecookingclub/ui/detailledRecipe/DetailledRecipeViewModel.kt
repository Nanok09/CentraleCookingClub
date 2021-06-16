package com.example.centralecookingclub.ui.detailledRecipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailledRecipeViewModel(application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>().apply {
        value = ""
    }
    val recipe = MutableLiveData<Recipe>().apply {
        value=null
    }
    private val cccRepository by lazy { CCCRepository.newInstance(application)}
    suspend fun getRecipe(id : Int){
        try {
            val temp = cccRepository.localDataSource.getRecipe(id)
            withContext(Main)
            {
                recipe.value = temp
                title.value= recipe.value!!.name
            }
        } catch (e: Exception){
            Log.d("CCC",e.toString())
        }
    }
}