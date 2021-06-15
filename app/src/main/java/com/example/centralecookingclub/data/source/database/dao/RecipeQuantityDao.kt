package com.example.centralecookingclub.data.source.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.centralecookingclub.data.model.RecipeQuantity

@Dao
interface RecipeQuantityDao {


    @Query("SELECT * FROM RECIPE_QUANTITY_TABLE WHERE idRecipe=:idRecipe")
    suspend fun getRecipeQuantity(idRecipe: Int): MutableList<RecipeQuantity>

}