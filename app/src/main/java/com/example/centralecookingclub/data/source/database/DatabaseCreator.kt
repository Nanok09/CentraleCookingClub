package com.example.centralecookingclub.data.source.database

import com.example.centralecookingclub.R
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.centralecookingclub.data.CCCRepository
import com.example.centralecookingclub.data.model.Ingredient
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.RecipeQuantity
import com.example.centralecookingclub.data.model.Step

class DatabaseCreator(application: Application, context: Context) {

    private val cccRepository by lazy {
        CCCRepository.newInstance(application)
    }

    private val imgCrepe: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.crepes)





        ///////////////////////////////
        //CREATION DE LA DB INITIALE //
        ///////////////////////////////
        //Ajoute des ingrédients définis à la database
        suspend fun initializeIngredients() {
            val egg = Ingredient(1, "egg")
            val milk = Ingredient(2, "milk")
            val flour = Ingredient(3, "flour")
            val salt = Ingredient(4, "salt")
            val sugar = Ingredient(5, "sugar")
            val butter = Ingredient(6, "butter")

            cccRepository.localDataSource.addIngredient(egg)
            cccRepository.localDataSource.addIngredient(milk)
            cccRepository.localDataSource.addIngredient(flour)
            cccRepository.localDataSource.addIngredient(salt)
            cccRepository.localDataSource.addIngredient(sugar)
            cccRepository.localDataSource.addIngredient(butter)
        }

        //Ajoute des recettes à la database
        suspend fun initialzeRecipes() {
            val crepes = Recipe(1, "crepes", 25, imgCrepe, 4)
            cccRepository.localDataSource.addRecipe(crepes)
        }

        //Ajoute des étapes à la database
        suspend fun initializeSteps() {
            val stepcrepes1 = Step(
                1, 1,
                "Mettez la farine dans un saladier avec le sel et le sucre.",
                "Mélanger farine + sel + sucre.",
                "/", imgCrepe
            )
            val stepcrepes2 = Step(
                1, 2,
                "Faites un puits au milieu et versez-y les œufs.",
                "Faites un puits au milieu et versez-y les œufs.",
                "/", imgCrepe
            )
            val stepcrepes3 = Step(
                1, 3,
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
                "/", imgCrepe
            )
            val stepcrepes4 = Step(
                1, 4,
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
                "/", imgCrepe
            )
            val stepcrepes5 = Step(
                1, 5,
                "Faites cuire les crêpes dans une poêle chaude (par précaution légèrement huilée si votre poêle à crêpes n'est pas anti-adhésive). Versez une petite louche de pâte dans la poêle, faites un mouvement de rotation pour répartir la pâte sur toute la surface. Posez sur le feu et quand le tour de la crêpe se colore en roux clair, il est temps de la retourner.",
                "Faire cuire et retourner.",
                "/", imgCrepe
            )
            val stepcrepes6 = Step(
                1, 6,
                "Laissez cuire environ une minute de ce côté et la crêpe est prête.",
                "Cuire 1min.",
                "/", imgCrepe
            )
            cccRepository.localDataSource.addStep(stepcrepes1)
            cccRepository.localDataSource.addStep(stepcrepes2)
            cccRepository.localDataSource.addStep(stepcrepes3)
            cccRepository.localDataSource.addStep(stepcrepes4)
            cccRepository.localDataSource.addStep(stepcrepes5)
            cccRepository.localDataSource.addStep(stepcrepes6)
        }

        suspend fun initializeRecipeQuantities() {
            val quantityEggCrepes = RecipeQuantity(1, 1, "int", 4)
            val quantityMilkCrepes = RecipeQuantity(2, 1, "volume", 1 / 2) //En litres
            val quantityFlourCrepes = RecipeQuantity(3, 1, "mass", 250)
            val quantitySaltCrepes = RecipeQuantity(4, 1, "int", 1)
            val quantitySugarCrepes = RecipeQuantity(5, 1, "mass", 50)
            val quantityButter = RecipeQuantity(6, 1, "mass", 50)
            cccRepository.localDataSource.addRecipeQuantity(quantityEggCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityMilkCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityFlourCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantitySaltCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantitySugarCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityButter)
        }


        //Permet d'initialiser toutes les tables
        suspend fun initializeAllTables() {
            initializeIngredients()
            initialzeRecipes()
            initializeSteps()
            initializeRecipeQuantities()
        }
    }
