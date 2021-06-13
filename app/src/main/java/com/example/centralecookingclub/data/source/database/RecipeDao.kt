package com.example.centralecookingclub.data.source.database

import androidx.room.Dao
import androidx.room.Query
import com.example.centralecookingclub.data.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM RECIPE_TABLE")
    suspend fun getAllRecipes(): MutableList<Recipe>

    @Query("SELECT * FROM RECIPE_TABLE WHERE id=:id")
    suspend fun getRecipe(id: Int): Recipe



}