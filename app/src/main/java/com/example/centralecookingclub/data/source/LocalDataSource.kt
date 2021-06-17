package com.example.centralecookingclub.data.source

import android.R
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.room.Room
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.data.source.database.CCCDatabase
import kotlin.coroutines.coroutineContext


class LocalDataSource (
    application: Application,
) {
    private val roomDatabase =
        Room.databaseBuilder(application, CCCDatabase::class.java, "room-database")
            .build()

    private val ingredientDao = roomDatabase.ingredientDao()
    private val stepDao = roomDatabase.stepDao()
    private val recipeDao = roomDatabase.recipeDao()
    private val recipeQuantityDao = roomDatabase.recipeQuantityDao()


    suspend fun getRecipeQuantity(idRecipe: Int) = recipeQuantityDao.getRecipeQuantity(idRecipe)
    suspend fun addRecipeQuantity(recipeQuantity: RecipeQuantity) = recipeQuantityDao.addRecipeQuantity(recipeQuantity)

    suspend fun getAllSteps() = stepDao.getAllSteps()
    suspend fun getStepsFromRecipe(idRecipe: Int) = stepDao.getStepsFromRecipe(idRecipe)
    suspend fun addStep(step: Step) = stepDao.addStep(step)




    suspend fun getAllRecipes() = recipeDao.getAllRecipes()
    suspend fun getRecipe(id: Int) = recipeDao.getRecipe(id)
    suspend fun addRecipe(reciepe: Recipe) = recipeDao.addRecipe(reciepe)

    suspend fun getAllIngredients() = ingredientDao.getAllIngredients()
    suspend fun getIngredient(id: Int) = ingredientDao.getIngredient(id)
    suspend fun addIngredient(ingredient: Ingredient) = ingredientDao.addIngredient(ingredient)
    suspend fun getIngredientsFromRecipe(idRecipe: Int) = ingredientDao.getIngredientsFromRecipe(idRecipe)
    suspend fun getLastId(): Int {
        return recipeDao.getLastId()
    }


}
