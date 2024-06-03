package com.example.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    id: Long,
    viewModel: WishlistViewModel,
    navController: NavHostController
)
{
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val title = if(id==0L) "Add Wish" else "Edit Wish"
    if(id== 0L)
    {
        viewModel.updateTitle("")
        viewModel.updateDescription("")
    }
    else
    {
        val currWish = viewModel.getWishByID(id).collectAsState(initial = Wish())
        viewModel.updateTitle(currWish.value.title)
        viewModel.updateDescription(currWish.value.description)
    }

    Scaffold(
        modifier= Modifier.fillMaxSize(),
        topBar= {TopBar(
            title = title,
            onBackClick = {navController.navigateUp()}
            )},
        scaffoldState = scaffoldState
    ) { paddingValues->
        Column(modifier=Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = {Text(text="title", color = MaterialTheme.colorScheme.onPrimary)},
                value = viewModel.titleAddEdit,
                onValueChange = { viewModel.updateTitle(it) }
                )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = {Text(text="description", color = MaterialTheme.colorScheme.onPrimary)},
                value = viewModel.descriptionAddEdit,
                onValueChange = { viewModel.updateDescription(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    if(viewModel.titleAddEdit.isNotEmpty() && viewModel.descriptionAddEdit.isNotEmpty()) {
                        viewModel.upsertWish(
                            Wish(
                                id=id,
                                title = viewModel.titleAddEdit,
                                description = viewModel.descriptionAddEdit
                            )
                        )
                        navController.navigateUp()

                    }
                    else if(viewModel.titleAddEdit.isEmpty()){
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Enter title to add Wish")
                        }
                    }
                    else
                    {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Enter description to add Wish")
                        }
                    }
                }
            ){
                Text(text = if(id==0L) "Add" else "Save")
            }
        }
    }
}