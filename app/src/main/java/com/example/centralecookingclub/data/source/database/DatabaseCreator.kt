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
    private val imgSpaghettiCarbonara: Bitmap = BitmapFactory.decodeResource(context.resources, R
        .drawable.spaghetticarbonara)




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
            val tomatoes = Ingredient(7, "tomatoes")
            cccRepository.localDataSource.addIngredient(egg)
            cccRepository.localDataSource.addIngredient(milk)
            cccRepository.localDataSource.addIngredient(flour)
            cccRepository.localDataSource.addIngredient(salt)
            cccRepository.localDataSource.addIngredient(sugar)
            cccRepository.localDataSource.addIngredient(butter)
            cccRepository.localDataSource.addIngredient(tomatoes)
        }

        //Ajoute des recettes à la database
        suspend fun initialzeRecipes() {
            val crepes = Recipe(1, "crepes", 25, imgCrepe, 4)
            val spaghettiCarbonara = Recipe(2, "spaghetti carbonara", 22, imgSpaghettiCarbonara, 4)
            cccRepository.localDataSource.addRecipe(crepes)
            cccRepository.localDataSource.addRecipe(spaghettiCarbonara)
        }

        //Ajoute des étapes à la database
        suspend fun initializeSteps() {

            //RECETTE DES CREPES
            val stepCrepes1 = Step(
                1, 1,
                "Mettez la farine dans un saladier avec le sel et le sucre.",
                "Mélanger farine + sel + sucre.",
                "/", imgCrepe
            )
            val stepCrepes2 = Step(
                1, 2,
                "Faites un puits au milieu et versez-y les œufs.",
                "Faites un puits au milieu et versez-y les œufs.",
                "/", imgCrepe
            )
            val stepCrepes3 = Step(
                1, 3,
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
                "/", imgCrepe
            )
            val stepCrepes4 = Step(
                1, 4,
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit..",
                "Commencez à mélanger doucement. Quand le mélange devient épais, ajoutez le lait froid petit à petit.",
                "/", imgCrepe
            )
            val stepCrepes5 = Step(
                1, 5,
                "Faites cuire les crêpes dans une poêle chaude (par précaution légèrement huilée si votre poêle à crêpes n'est pas anti-adhésive). Versez une petite louche de pâte dans la poêle, faites un mouvement de rotation pour répartir la pâte sur toute la surface. Posez sur le feu et quand le tour de la crêpe se colore en roux clair, il est temps de la retourner.",
                "Faire cuire et retourner.",
                "/", imgCrepe
            )
            val stepCrepes6 = Step(
                1, 6,
                "Laissez cuire environ une minute de ce côté et la crêpe est prête.",
                "Cuire 1min.",
                "/", imgCrepe
            )

            cccRepository.localDataSource.addStep(stepCrepes1)
            cccRepository.localDataSource.addStep(stepCrepes2)
            cccRepository.localDataSource.addStep(stepCrepes3)
            cccRepository.localDataSource.addStep(stepCrepes4)
            cccRepository.localDataSource.addStep(stepCrepes5)
            cccRepository.localDataSource.addStep(stepCrepes6)

            //Recette des pâtes carbonaras

            val stepCarbonara1 = Step(2, 1,
                "Portez à ébullition un faitout d'eau salée. Plongez-y les spaghetti et " +
                        "laissez-les cuire environ 12 min, jusqu'à ce qu'ils soient al dente.",
            "Portez à ébullition un faitout d'eau salée. Plongez-y les spaghetti et laissez-les " +
                    "cuire environ 12 min, jusqu'à ce qu'ils soient al dente.",
            "/",
            imgSpaghettiCarbonara)

            val stepCarbonara2 = Step(2, 2,
                "Pendant la cuisson des spaghetti, faites revenir les lardons à sec dans une poêle, jusqu'à ce qu'ils soient bien dorés.",
                "Pendant la cuisson des spaghetti, faites revenir les lardons à sec dans une poêle, jusqu'à ce qu'ils soient bien dorés.",
                "/",
                imgSpaghettiCarbonara)

            val stepCarbonara3 = Step(2, 3,
                "Baissez le feu et incorporez la crème fraîche. Salez légèrement, poivrez généreusement et ajoutez les jaunes d'oeufs, en fouettant pour qu'ils ne cuisent pas.",
                "Baissez le feu et incorporez la crème fraîche. Salez légèrement, poivrez généreusement et ajoutez les jaunes d'oeufs, en fouettant pour qu'ils ne cuisent pas.",
                "/",
                imgSpaghettiCarbonara)

            val stepCarbonara4 = Step(2, 4,
                "Rectifiez l'assaisonnement.",
                "Rectifiez l'assaisonnement.",
                "/",
                imgSpaghettiCarbonara)

            val stepCarbonara5 = Step(2, 5,
                "Egouttez les pâtes. Versez-les dans la sauteuse, mélangez et transvasez dans un " +
                        "plat de service.",
            "Egouttez les pâtes. Versez-les dans la sauteuse, mélangez et transvasez dans un plat" +
                    " de service.",
            "/",
            imgSpaghettiCarbonara)

            val stepCarbonara6 = Step (2, 6, "Servez en présentant le parmesan à part.",
                "Servez en présentant le parmesan à part.", "/", imgSpaghettiCarbonara
            )

            cccRepository.localDataSource.addStep(stepCarbonara1)
            cccRepository.localDataSource.addStep(stepCarbonara2)
            cccRepository.localDataSource.addStep(stepCarbonara3)
            cccRepository.localDataSource.addStep(stepCarbonara4)
            cccRepository.localDataSource.addStep(stepCarbonara5)
            cccRepository.localDataSource.addStep(stepCarbonara6)
        }

        suspend fun initializeRecipeQuantities() {

            //CREPES
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

            //PATES CARBO
        }


        //Permet d'initialiser toutes les tables
        suspend fun initializeAllTables() {
            initializeIngredients()
            initialzeRecipes()
            initializeSteps()
            initializeRecipeQuantities()
        }
    }
