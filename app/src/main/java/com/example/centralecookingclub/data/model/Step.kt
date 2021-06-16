package com.example.centralecookingclub.data.model

import android.graphics.Bitmap
import androidx.room.Entity

@Entity(tableName = "STEP_TABLE", primaryKeys = ["idRecipe", "stepNumber"])
data class Step(


    val idRecipe: Int,
    val stepNumber: Int,
    val description: String,
    val descriptionShort: String,
    val imgPath: String,
    val stepimg: Bitmap



)
