package com.example.centralecookingclub.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.centralecookingclub.data.model.Ingredient

@Dao
interface IngredientDao {

    @Query("SELECT * FROM INGREDIENT_TABLE")
    suspend fun getAllIngredients(): MutableList<Ingredient>

    @Query("SELECT * FROM INGREDIENT_TABLE WHERE id=:id")
    suspend fun getIngredient(id: Int): Ingredient

    @Query(
        "SELECT INGREDIENT_TABLE.id, INGREDIENT_TABLE.name, INGREDIENT_TABLE.unit FROM " +
                "INGREDIENT_TABLE INNER JOIN " +
                "RECIPE_QUANTITY_TABLE ON INGREDIENT_TABLE.id = RECIPE_QUANTITY_TABLE.idIngredient WHERE RECIPE_QUANTITY_TABLE.idRecipe = :idRecipe"
    )
    suspend fun getIngredientsFromRecipe(idRecipe: Int): MutableList<Ingredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredient(ingredient: Ingredient)


}
