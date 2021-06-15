package com.example.centralecookingclub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INGREDIENT_TABLE")
data class Ingredient(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String
)
