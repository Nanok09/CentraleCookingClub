package com.example.centralecookingclub.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.centralecookingclub.data.model.Recipe
import com.example.centralecookingclub.data.model.Step

@Dao
interface  StepDao {
    @Query("SELECT * FROM STEP_TABLE")
    suspend fun getAllSteps(): MutableList<Step>

    @Query("SELECT * FROM STEP_TABLE WHERE idRecipe = :idRecipe")
    suspend fun getAllSteps(idRecipe: Int): MutableList<Step>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStep(step: Step)

    @Query("SELECT * FROM STEP_TABLE WHERE idRecipe = :idRecipe")
    suspend fun getStepsFromRecipe(idRecipe: Int): MutableList<Step>
}