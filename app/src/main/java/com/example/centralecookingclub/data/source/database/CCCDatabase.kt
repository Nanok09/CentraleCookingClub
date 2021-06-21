package com.example.centralecookingclub.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.data.model.RecipeQuantity
import androidx.room.TypeConverters
import com.example.centralecookingclub.data.model.*
import com.example.centralecookingclub.data.source.database.dao.*


@Database(
    entities = [
        Ingredient::class,
        Recipe::class,
        RecipeQuantity::class,
        Step::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CCCDatabase: RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun stepDao(): StepDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeQuantityDao(): RecipeQuantityDao
    abstract fun shoppingListItemDao() : ShoppingListItemDao
}