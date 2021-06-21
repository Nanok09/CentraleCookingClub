package com.example.centralecookingclub.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.centralecookingclub.data.model.Recipe

@Dao
interface ShoppingListItemDao {
    @Query("SELECT * FROM SHOPPING_LIST_ITEM_TABLE")
    suspend fun getAllShoppingListItems()

    @Query("UPDATE SHOPPING_LIST_ITEM_TABLE SET bought= :value WHERE idItem=:idItem")
    suspend fun changeBought(idItem: Int, value: Int)
}