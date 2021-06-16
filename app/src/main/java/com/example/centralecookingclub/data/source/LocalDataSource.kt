package com.example.centralecookingclub.data.source

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.data.source.database.CCCDatabase

class LocalDataSource (
    application: Application
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

    ///////////////////////////////
    //CREATION DE LA DB INITIALE //
    ///////////////////////////////
    //Ajoute des ingrédients définis à la database

    suspend fun initializeIngredients(){
        val egg = Ingredient (1, "egg")
        val milk = Ingredient(2, "milk")
        val flour = Ingredient(3, "flour")
        val salt = Ingredient(4, "salt")
        val sugar = Ingredient(5, "sugar")
        val butter = Ingredient(6, "butter")
        addIngredient(egg)
        addIngredient(milk)
        addIngredient(flour)
        addIngredient(salt)
        addIngredient(sugar)
        addIngredient(butter)
    }

    //Ajoute des recettes à la database
    suspend fun initialzeRecipes(){
        val crepes = Recipe(1,"crepes", 25, "test", 4)
        addRecipe(crepes)
    }

    //Ajoute des étapes à la database
    suspend fun initializeSteps(){
        val stepcrepes1 = Step(1,1,
            "Mettez la farine dans un saladier avec le sel et le sucre.",
            "Mélanger farine + sel + sucre.",
            "/"
        )
        val stepcrepes2 = Step(1,2,
            "Faites un puits au milieu et versez-y les œufs.",
            "Faites un puits au milieu et versez-y les œufs.",
            "/"
        )
        val stepcrepes3 = Step(1,3,
            "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
            "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
            "/"
        )
        val stepcrepes4 = Step(1,4,
            "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
            "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
            "/"
        )
        val stepcrepes5 = Step(1,5,
            "Faites cuire les crêpes dans une poêle chaude (par précaution légèrement huilée si votre poêle à crêpes n'est pas anti-adhésive). Versez une petite louche de pâte dans la poêle, faites un mouvement de rotation pour répartir la pâte sur toute la surface. Posez sur le feu et quand le tour de la crêpe se colore en roux clair, il est temps de la retourner.",
            "Faire cuire et retourner.",
            "/"
        )
        val stepcrepes6 = Step(1,6,
            "Laissez cuire environ une minute de ce côté et la crêpe est prête.",
            "Cuire 1min.",
            "/"
        )
        addStep(stepcrepes1)
        addStep(stepcrepes2)
        addStep(stepcrepes3)
        addStep(stepcrepes4)
        addStep(stepcrepes5)
        addStep(stepcrepes6)
    }

    suspend fun initializeRecipeQuantities(){
        val quantityEggCrepes = RecipeQuantity(1, 1, "int", 4)
        val quantityMilkCrepes = RecipeQuantity(2, 1, "volume", 1/2) //En litres
        val quantityFlourCrepes = RecipeQuantity(3, 1, "mass", 250)
        val quantitySaltCrepes = RecipeQuantity(4, 1, "int", 1 )
        val quantitySugarCrepes = RecipeQuantity(5,1, "mass", 50)
        val quantityButter = RecipeQuantity(6, 1, "mass", 50)
        addRecipeQuantity(quantityEggCrepes)
        addRecipeQuantity(quantityMilkCrepes)
        addRecipeQuantity(quantityFlourCrepes)
        addRecipeQuantity(quantitySaltCrepes)
        addRecipeQuantity(quantitySugarCrepes)
        addRecipeQuantity(quantityButter)
    }



    //Permet d'initialiser toutes les tables
    suspend fun initializeAllTables(){
        initializeIngredients()
        initialzeRecipes()
        initializeSteps()
        initializeRecipeQuantities()
    }

}
