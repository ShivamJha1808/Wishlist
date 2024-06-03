package com.example.wishlist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Wish::class],
    version =1
)
abstract class WishlistDatabase: RoomDatabase() {
    abstract val wishDao: WishlistDao
}