package com.example.centralecookingclub.data.source

import android.app.Application
import androidx.room.Room
import com.example.centralecookingclub.data.source.database.CCCDatabase

class LocalDataSource (
    application: Application
) {
    private val roomDatabase =
        Room.databaseBuilder(application, CCCDatabase::class.java, "room-database").build()


    private val ingredientDao = roomDatabase.ingredientDao()
    private val stepDao = roomDatabase.stepDao()
    private val recipeDao = roomDatabase.recipeDao()
    private val recipeQuantityDao = roomDatabase.recipeQuantityDao()


    suspend fun getRecipeQuantity(idRecipe: Int) = recipeQuantityDao.getRecipeQuantity(idRecipe)

    suspend fun getAllSteps() = stepDao.getAllSteps()

    suspend fun getAllRecipes() = recipeDao.getAllRecipes()
    suspend fun getRecipe(id: Int) = recipeDao.getRecipe(id)

    suspend fun getAllIngredients() = ingredientDao.getAllIngredients()
    suspend fun getIngredient(id: Int) = ingredientDao.getIngredient(id)



}