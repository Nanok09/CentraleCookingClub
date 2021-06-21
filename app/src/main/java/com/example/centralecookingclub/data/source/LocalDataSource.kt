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


    //////////////////////////////////////////
    // OPERATIONS GENERALES SUR LA DATABASE //
    //////////////////////////////////////////
    // Contactez Shanly si vous pouvez pas faire vos bails avec les fonctions existantes

    suspend fun getRecipeQuantity(idRecipe: Int) = recipeQuantityDao.getRecipeQuantity(idRecipe)
    suspend fun addRecipeQuantity(recipeQuantity: RecipeQuantity) = recipeQuantityDao.addRecipeQuantity(recipeQuantity)
    suspend fun getQuantityOfIngredients(idIngredient: Int) = recipeQuantityDao
        .getQuantityOfIngredient(idIngredient)

    suspend fun getAllSteps() = stepDao.getAllSteps()
    suspend fun getStepsFromRecipe(idRecipe: Int) = stepDao.getStepsFromRecipe(idRecipe)
    suspend fun addStep(step: Step) = stepDao.addStep(step)


    suspend fun getAllRecipes() = recipeDao.getAllRecipes()
    suspend fun getRecipe(id: Int) = recipeDao.getRecipe(id)
    suspend fun addRecipe(recipe: Recipe) = recipeDao.addRecipe(recipe)
    suspend fun searchRecipe(name : String) = recipeDao.searchRecipe(name)

    suspend fun getAllIngredients() = ingredientDao.getAllIngredients()
    suspend fun getIngredient(id: Int) = ingredientDao.getIngredient(id)
    suspend fun addIngredient(ingredient: Ingredient) = ingredientDao.addIngredient(ingredient)
    suspend fun getIngredientsFromRecipe(idRecipe: Int) = ingredientDao.getIngredientsFromRecipe(idRecipe)
    suspend fun getLastId(): Int {
        return recipeDao.getLastId()
    }

    //////////////////////////////////////
    // Récupération de données précises //
    //////////////////////////////////////
    // Tout le monde peut écrire ici
    // Mettez au max vos fonctions ici et pas ailleurs dans le code (UI)
    // Vous pouvez réutiliser les fonctions du dessus mais il vaut mieux passer les dataclass en
    // paramètre !

    suspend fun getRecipeNbPeople(recipe:Recipe) = recipe.numberOfPeople

    suspend fun scaleQuantitiesOfRecipe(recipe:Recipe, newNbPeople:Int) :
            MutableList<RecipeQuantity>{
        val idRecipe = recipe.id
        val nbPeople = recipe.numberOfPeople
        val listIngredient = getIngredientsFromRecipe(idRecipe)
        val newListQuantities : MutableList<RecipeQuantity> = arrayListOf()

        listIngredient.forEach {
            val idCurrentIngredient = it.id
            val quantityOfIngredient = getQuantityOfIngredients(idCurrentIngredient)
            val newQuantity = quantityOfIngredient.quantity * (newNbPeople / nbPeople)
            val newRecipeQuantity = RecipeQuantity(it.id, idRecipe, quantityOfIngredient.unit, newQuantity)
            // y'aura ptet une erreur ici car c'est pas un entier newQuantity
            newListQuantities.add(newRecipeQuantity)
        }

        return newListQuantities
    }

    suspend fun changeFaved(recipe: Recipe){
        val idRecipe = recipe.id
        val faved = recipe.faved
        recipeDao.changeFaved(idRecipe, !faved)
    }
}
