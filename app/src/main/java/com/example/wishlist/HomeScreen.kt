package com.example.wishlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: WishlistViewModel,
    navController: NavHostController
)
{
    val listOfWishes = viewModel.allWishes.collectAsState(initial = listOf())


    Scaffold(
        modifier= Modifier.fillMaxSize(),
        topBar= {TopBar(title = "Wishlist")},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ScreenRoute.AddEditScreen.route+"/0L")
                }
                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Wishes")
            }
        }
    ) { paddingValues->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
            state = rememberLazyListState()
        )
        {
            items(listOfWishes.value, key = {wish-> wish.id})
            {wish->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if( it==androidx.compose.material.DismissValue.DismissedToStart)
                        {
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {

                        val color by animateColorAsState(
                            when(dismissState.dismissDirection)
                            {
                                DismissDirection.EndToStart-> Color(212,21,34)
                                else-> Color.Transparent
                            },
                            label = "back ground color"
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp,start = 8.dp,end = 8.dp)
                                .background(color, RoundedCornerShape(5.dp))
                                .fillMaxSize()
                                .padding(end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Wish",
                                tint = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Wish",
                                tint = Color.White
                            )
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.5f)}
                ) {
                    WishCard(
                        wish = wish,
                        onEditClick = {
                            navController.navigate(ScreenRoute.AddEditScreen.route+"/${wish.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteWish(wish)
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun WishCard(
    wish : Wish,
    onEditClick:()->Unit,
    onDeleteClick: ()->Unit)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(text = wish.title, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = wish.description, fontSize = 14.sp)
            }
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit current wish",
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
            IconButton(onClick = { onDeleteClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete current wish",
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    }
}

object Sample
{
    val sampleWishes = listOf<Wish>(
        Wish(1,"wish1","des1"),
        Wish(2,"wish2","des2"),
        Wish(3,"wish3","des3")
    ) 
}