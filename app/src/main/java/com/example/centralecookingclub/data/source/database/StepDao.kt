package com.example.centralecookingclub.data.source.database

import androidx.room.Dao
import androidx.room.Query
import com.example.centralecookingclub.data.model.Step

@Dao
interface StepDao {
    @Query("SELECT * FROM STEP_TABLE")
    suspend fun getAllSteps(): MutableList<Step>
}