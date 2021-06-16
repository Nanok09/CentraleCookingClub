package com.example.centralecookingclub.ui.gallery

import android.app.Application
import androidx.lifecycle.*
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    var editRecipeList = MutableLiveData<MutableList<EditRecipe>>()
    val text: LiveData<String> = _text

    private val cccRepository by lazy { CCCRepository.newInstance(application)}

    val steps = MutableLiveData<ViewState>()
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

    sealed class ViewState{
        object Loading : ViewState()
        data class Content(val steps: MutableList<Step>) : ViewState()
        data class Error(val message: String) : ViewState()
    }

}