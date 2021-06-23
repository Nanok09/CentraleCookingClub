package com.example.centralecookingclub.data

import android.app.Application
import android.content.Context
import com.example.centralecookingclub.data.model.Step
import com.example.centralecookingclub.data.source.LocalDataSource

class CCCRepository (
    val localDataSource: LocalDataSource,
    ){

    suspend fun getAllSteps(id:Int) : MutableList<Step>{
        return localDataSource.getAllSteps(id)
    }

    companion object {
        fun newInstance(application: Application): CCCRepository{
            return CCCRepository(localDataSource = LocalDataSource(application))
            TODO("initialiser la DB une fois LocalDataSource créée.")
        }
    }

}