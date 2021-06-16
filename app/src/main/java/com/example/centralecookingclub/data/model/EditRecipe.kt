package com.example.centralecookingclub.data.model



data class EditRecipe(
    val name: String,
    val description: String,
    val imgPath: String,
    var numOfStep: Int
)