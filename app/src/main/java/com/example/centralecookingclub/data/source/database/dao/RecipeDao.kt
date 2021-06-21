package com.example.centralecookingclub.data.source.database.dao

import androidx.room.*
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT MAX(id) FROM RECIPE_TABLE")
    suspend fun getLastId(): Int

    @Query("SELECT * FROM RECIPE_TABLE")
    suspend fun getAllRecipes(): MutableList<Recipe>

    @Query("SELECT * FROM RECIPE_TABLE WHERE id=:id")
    suspend fun getRecipe(id: Int): Recipe

    @Query("UPDATE RECIPE_TABLE SET faved = :value WHERE id=:idRecipe")
    suspend fun changeFaved(idRecipe: Int, value: Int)

    @Query("SELECT * FROM RECIPE_TABLE WHERE name LIKE '%'+:name+'%' ")
    suspend fun searchRecipe(name:String): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)



}