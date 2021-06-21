package com.example.centralecookingclub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SHOPPING_LIST_ITEM_TABLE")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    val idItem: Int,
    val name: String,
    val bought: Int = 0
)
