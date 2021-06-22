package com.example.centralecookingclub.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT MAX(id) FROM RECIPE_TABLE")
    suspend fun getLastId(): Int

    @Query("SELECT * FROM RECIPE_TABLE")
    suspend fun getAllRecipes(): MutableList<Recipe>

    @Query("SELECT * FROM RECIPE_TABLE WHERE name=:name")
    suspend fun getAllRecipesByName(name : String ): MutableList<Recipe>

    @Query("SELECT * FROM RECIPE_TABLE WHERE id=:id")
    suspend fun getRecipe(id: Int): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)



}