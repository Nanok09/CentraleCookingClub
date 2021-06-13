package com.example.centralecookingclub.data.source.database

import androidx.room.Dao
import androidx.room.Query
import com.example.centralecookingclub.data.model.Ingredient

@Dao
interface IngredientDao {



        @Query("SELECT * FROM INGREDIENT_TABLE")
        suspend fun getAllIngredients(): MutableList<Ingredient>

        @Query("SELECT * FROM INGREDIENT_TABLE WHERE id=:id")
        suspend fun getIngredient(id: Int): Ingredient



    }
