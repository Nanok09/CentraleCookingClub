package com.example.centralecookingclub.data.source

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.centralecookingclub.data.source.database.CCCDatabase

class LocalDataSource (
    application: Application
) {
    private val roomDatabase by lazy {
        Room.databaseBuilder(application, CCCDatabase::class.java, "room-database")
            .addCallback(rdc)
            .build()
    }

    private val rdc = object : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase){
            super.onCreate(db)


            val ingredients: Array<String> = arrayOf("egg", "milk", "ham", "salt", "sugar")

            ingredients.forEach {
                db.execSQL("INSERT INTO INGREDIENT_TABLE (name) VALUES (it)")
            }

        }

    }

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