package com.example.centralecookingclub.data

import android.app.Application
import com.example.centralecookingclub.data.source.LocalDataSource

class CCCRepository (
    val localDataSource: LocalDataSource

    ){

    companion object {
        fun newInstance(application: Application): CCCRepository{
            return CCCRepository(localDataSource = LocalDataSource(application))
        }
    }

}