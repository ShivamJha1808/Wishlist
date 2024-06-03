package com.example.wishlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao
{
    @Upsert
    suspend fun upsertWish(wish: Wish)

    @Delete
    suspend fun deleteWish(wish: Wish)

    @Query("SELECT * FROM `wish_table`")
    fun getAllWishes() : Flow<List<Wish>>

    @Query("SELECT * FROM `wish_table` WHERE id=:id")
    fun getWishByID(id:Long) : Flow<Wish>
}