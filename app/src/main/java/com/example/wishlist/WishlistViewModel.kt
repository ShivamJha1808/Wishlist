package com.example.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val wishRepository: WishlistRepository = Graph.wishRepository
): ViewModel() {
    private val _titleAddEdit = mutableStateOf("")
    private val _descriptionAddEdit = mutableStateOf("")
    var allWishes : Flow<List<Wish>> = wishRepository.getAllWishes()




    val titleAddEdit by _titleAddEdit
    val descriptionAddEdit by _descriptionAddEdit

    fun getWishByID(id:Long): Flow<Wish>
    {
        return wishRepository.getWishByID(id)
    }

    fun upsertWish(wish:Wish)
    {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.upsertWish(wish=wish)
        }
    }

    fun deleteWish(wish:Wish)
    {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

    fun updateTitle(newTitle: String)
    {
        _titleAddEdit.value=newTitle
    }

    fun updateDescription(newDescription: String)
    {
        _descriptionAddEdit.value=newDescription
    }

}