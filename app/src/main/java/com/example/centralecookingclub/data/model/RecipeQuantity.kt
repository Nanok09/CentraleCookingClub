package com.example.centralecookingclub.data.model

import androidx.room.Entity

@Entity(tableName = "RECIPE_QUANTITY_TABLE", primaryKeys = ["idIngredient", "idRecipe"])
data class RecipeQuantity(
    val idIngredient: Int,
    val idRecipe: Int,
    val unit: String,
    var quantity: Int
)
