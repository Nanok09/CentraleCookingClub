package com.example.centralecookingclub.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.centralecookingclub.data.model.EditRecipe
import com.example.centralecookingclub.data.model.Recipe

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    var editRecipeList = MutableLiveData<MutableList<EditRecipe>>()
    val text: LiveData<String> = _text
}