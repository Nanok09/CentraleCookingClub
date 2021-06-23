package com.example.centralecookingclub.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RECIPE_TABLE")
data class Recipe(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Int,
    val recipeImage: Bitmap,
    var numberOfPeople: Int,
    val authorRecipe: String,
    var faved: Int = 0,

)
