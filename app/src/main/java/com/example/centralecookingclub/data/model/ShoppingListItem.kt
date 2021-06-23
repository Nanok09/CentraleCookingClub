package com.example.centralecookingclub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SHOPPING_LIST_ITEM_TABLE")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    val idItem: Int,
    val name: String,
    var bought: Int = 0
)
