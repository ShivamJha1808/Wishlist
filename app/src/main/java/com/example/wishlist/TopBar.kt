package com.example.wishlist

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    onBackClick: ()->Unit ={},
)
{
    val navigation : (@Composable() ()->Unit)? =
        if(title!="Wishlist")
        {
            {
                IconButton(onClick = {onBackClick()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        else {
            null
        }

    TopAppBar(
        title = {Text(text=title, color = MaterialTheme.colorScheme.onPrimary)},
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colorScheme.primary,
        navigationIcon = navigation
        )

}