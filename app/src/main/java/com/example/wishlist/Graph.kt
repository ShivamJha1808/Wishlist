package com.example.wishlist

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: WishlistDatabase

    fun provide(context: Context)
    {
        database = Room.databaseBuilder(context,WishlistDatabase::class.java,"wishlist_database.db").build()
    }

    val wishRepository: WishlistRepository by lazy {
        WishlistRepository(database.wishDao)
    }


}