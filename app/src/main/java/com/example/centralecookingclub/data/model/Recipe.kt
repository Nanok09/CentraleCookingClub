package com.example.centralecookingclub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RECIPE_TABLE")
data class Recipe(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Int,
    val imgPath: String,
    var numberOfPeople: Int

)
