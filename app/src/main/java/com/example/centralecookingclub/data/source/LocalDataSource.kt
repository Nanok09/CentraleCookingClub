package com.example.centralecookingclub.data.source

import android.R
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.room.Query
import androidx.room.Room
import com.example.centralecookingclub.data.model.*
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
    private val shoppingListItemDao = roomDatabase.shoppingListItemDao()

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
    suspend fun getAllRecipesByName(name : String) = recipeDao.getAllRecipesByName(name)
    suspend fun researchInFavorites(name : String) = recipeDao.researchInFavorites(name)
    suspend fun getRecipe(id: Int) = recipeDao.getRecipe(id)
    suspend fun addRecipe(recipe: Recipe) = recipeDao.addRecipe(recipe)

    suspend fun getAllIngredients() = ingredientDao.getAllIngredients()
    suspend fun getIngredient(id: Int) = ingredientDao.getIngredient(id)
    suspend fun addIngredient(ingredient: Ingredient) = ingredientDao.addIngredient(ingredient)
    suspend fun getIngredientsFromRecipe(idRecipe: Int) = ingredientDao.getIngredientsFromRecipe(idRecipe)
    suspend fun getLastId(): Int {
        return recipeDao.getLastId()
    }

    suspend fun getAllShoppingListItems() = shoppingListItemDao.getAllShoppingListItems()
    suspend fun addShoppingListItem(shoppingListItem: ShoppingListItem) = shoppingListItemDao
        .addShoppingListItem(shoppingListItem)
    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) = shoppingListItemDao
        .deleteShoppingListItem(shoppingListItem)
    suspend fun getLastIdItem(): Int {
        var id = shoppingListItemDao.getLastIdItem()
        if (id == null) return 0
        else return id
    }
    //////////////////////////////////////
    // R??cup??ration de donn??es pr??cises //
    //////////////////////////////////////
    // Tout le monde peut ??crire ici
    // Mettez au max vos fonctions ici et pas ailleurs dans le code (UI)
    // Vous pouvez r??utiliser les fonctions du dessus mais il vaut mieux passer les dataclass en
    // param??tre !

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
            Log.d("CCC",quantityOfIngredient.toString())
            val ratioPeople = (newNbPeople*1F / nbPeople*1F)
            val newQuantity = quantityOfIngredient.quantity * (ratioPeople)
            Log.d("CCC",newQuantity.toString())
            val newRecipeQuantity = RecipeQuantity(it.id, idRecipe, quantityOfIngredient.unit, newQuantity)
            // y'aura ptet une erreur ici car c'est pas un entier newQuantity
            newListQuantities.add(newRecipeQuantity)
        }

        return newListQuantities
    }

    suspend fun getAllRecipeNames(): Array<String> {
        val listOfRecipes = getAllRecipes()
        return Array(listOfRecipes.size) { i -> listOfRecipes[i].name }
    }

    suspend fun changeFaved(recipe: Recipe){
        val idRecipe = recipe.id
        val faved = recipe.faved
        recipeDao.changeFaved(idRecipe, if (faved == 0) 1 else 0)
    }

    suspend fun changeBought(shoppingListItem: ShoppingListItem){
        val idItem = shoppingListItem.idItem
        val bought = shoppingListItem.bought
        shoppingListItemDao.changeBought(idItem, if (bought == 0) 1 else 0)
    }
}
