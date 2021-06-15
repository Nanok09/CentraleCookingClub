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




    @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addIngredient(ingredient: Ingredient)

    }
