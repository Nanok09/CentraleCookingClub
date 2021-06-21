package com.example.centralecookingclub.data.source.database.dao

import androidx.room.*
import com.example.centralecookingclub.data.model.ShoppingListItem

@Dao
interface ShoppingListItemDao {
    @Query("SELECT * FROM SHOPPING_LIST_ITEM_TABLE")
    suspend fun getAllShoppingListItems()

    @Query("UPDATE SHOPPING_LIST_ITEM_TABLE SET bought= :value WHERE idItem=:idItem")
    suspend fun changeBought(idItem: Int, value: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingListItem(shoppingListItem : ShoppingListItem)

    @Delete
    suspend fun deleteShoppingListItem(shoppingListItem : ShoppingListItem)
}
}