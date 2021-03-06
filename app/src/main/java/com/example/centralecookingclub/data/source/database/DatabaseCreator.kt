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
            //CREPES
            val egg = Ingredient(1, "egg", "unit")
            val milk = Ingredient(2, "milk", "L")
            val flour = Ingredient(3, "flour", "g")
            val salt = Ingredient(4, "salt", "pincée")
            val sugar = Ingredient(5, "sugar", "g")
            val butter = Ingredient(6, "butter", "g")
            cccRepository.localDataSource.addIngredient(egg)
            cccRepository.localDataSource.addIngredient(milk)
            cccRepository.localDataSource.addIngredient(flour)
            cccRepository.localDataSource.addIngredient(salt)
            cccRepository.localDataSource.addIngredient(sugar)
            cccRepository.localDataSource.addIngredient(butter)

            //Autre
            val tomatoes = Ingredient(7, "tomatoes", "g")
            cccRepository.localDataSource.addIngredient(tomatoes)

            //PATES CARBO
            val dicedbacon = Ingredient(8, "diced bacon", "g")
            val parmesan = Ingredient(9, "parmesan", "g")
            val pepper = Ingredient(10, "pepper", "g")
            val spaghetti = Ingredient(11, "spaghetti", "g")
            val eggyolks = Ingredient(12, "egg yolks", "unit")
            val cream = Ingredient(13, "cream", "g")

            cccRepository.localDataSource.addIngredient(dicedbacon)
            cccRepository.localDataSource.addIngredient(parmesan)
            cccRepository.localDataSource.addIngredient(pepper)
            cccRepository.localDataSource.addIngredient(spaghetti)
            cccRepository.localDataSource.addIngredient(eggyolks)
            cccRepository.localDataSource.addIngredient(cream)
        }

        //Ajoute des recettes à la database
        suspend fun initialzeRecipes() {
            val crepes = Recipe(1, "crepes", 25, imgCrepe, 4, "mamie", 0)
            val spaghettiCarbonara = Recipe(2, "spaghetti carbonara", 22, imgSpaghettiCarbonara,
                4, "mamie", 0)
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
                "Mélanger doucement. Puis ajouter lait.",
                "/", imgCrepe
            )
            val stepCrepes4 = Step(
                1, 4,
                "Dans une poële chaude, versez une petite louche de pâte dans la poêle, répartir la pâte. Posez sur le feu et quand le tour de la crêpe se colore en roux clair, il est temps de la retourner.",
                "Faire cuire et retourner.",
                "/", imgCrepe
            )
            val stepCrepes5 = Step(
                1, 5,
                "Laissez cuire environ une minute de ce côté et la crêpe est prête.",
                "Cuire 1min.",
                "/", imgCrepe
            )

            cccRepository.localDataSource.addStep(stepCrepes1)
            cccRepository.localDataSource.addStep(stepCrepes2)
            cccRepository.localDataSource.addStep(stepCrepes3)
            cccRepository.localDataSource.addStep(stepCrepes4)
            cccRepository.localDataSource.addStep(stepCrepes5)

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
            val quantityEggCrepes = RecipeQuantity(1, 1, "int", 4F)
            val quantityMilkCrepes = RecipeQuantity(2, 1, "volume", 1F/2F) //En litres
            val quantityFlourCrepes = RecipeQuantity(3, 1, "mass", 250F)
            val quantitySaltCrepes = RecipeQuantity(4, 1, "int", 1F)
            val quantitySugarCrepes = RecipeQuantity(5, 1, "mass", 50F)
            val quantityButterCrepes = RecipeQuantity(6, 1, "mass", 50F)
            cccRepository.localDataSource.addRecipeQuantity(quantityEggCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityMilkCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityFlourCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantitySaltCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantitySugarCrepes)
            cccRepository.localDataSource.addRecipeQuantity(quantityButterCrepes)

            //PATES CARBO
            val quantityDicedBaconCarbonara = RecipeQuantity(8,2, "mass", 160F)
            val quantityParmesanCarbonara = RecipeQuantity(9, 2, "mass", 60F)
            val quantityPepperCarbonara = RecipeQuantity(10, 2, "int", 1F)
            val quantitySpaghettiCarbonara = RecipeQuantity(11, 2, "mass", 200F)
            val quantityEggYolksCarbonara = RecipeQuantity(12, 2, "int", 2F)
            val quantityCreamCarbonara = RecipeQuantity(13, 2, "volume", 15F)
            val quantitySaltCarbonara= RecipeQuantity(4, 2, "int", 1F)

            cccRepository.localDataSource.addRecipeQuantity(quantityDicedBaconCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantityParmesanCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantityPepperCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantitySpaghettiCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantityEggYolksCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantityCreamCarbonara)
            cccRepository.localDataSource.addRecipeQuantity(quantitySaltCarbonara)
        }


        //Permet d'initialiser toutes les tables
        suspend fun initializeAllTables() {
            initializeIngredients()
            initialzeRecipes()
            initializeSteps()
            initializeRecipeQuantities()
        }
    }
