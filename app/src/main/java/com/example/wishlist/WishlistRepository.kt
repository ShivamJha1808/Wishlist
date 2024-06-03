package com.example.wishlist

import kotlinx.coroutines.flow.Flow

class WishlistRepository( val wishlistDao: WishlistDao)
{
    suspend fun upsertWish(wish: Wish)
    {
        wishlistDao.upsertWish(wish)
    }

    suspend fun deleteWish(wish: Wish)
    {
        wishlistDao.deleteWish(wish)
    }

    fun getAllWishes():Flow<List<Wish>>
    {
        return wishlistDao.getAllWishes()
    }

    fun getWishByID(id:Long):Flow<Wish>
    {
        return wishlistDao.getWishByID(id)
    }
}