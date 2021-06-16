package com.example.centralecookingclub.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.source.database.dao.IngredientDao
import com.example.centralecookingclub.data.source.database.dao.RecipeDao
import com.example.centralecookingclub.data.source.database.dao.RecipeQuantityDao
import com.example.centralecookingclub.data.source.database.dao.StepDao


@Database(
    entities = [
        Ingredient::class,
        Recipe::class,
        RecipeQuantity::class,
        Step::class
    ],
    version = 1
)
abstract class CCCDatabase: RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun stepDao(): StepDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeQuantityDao(): RecipeQuantityDao
}