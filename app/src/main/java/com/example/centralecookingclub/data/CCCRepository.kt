package com.example.centralecookingclub.data

import android.app.Application
import com.example.centralecookingclub.data.source.LocalDataSource

object CCCRepository {

    val localDataSource = LocalDataSource(application = Application())

}