package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GameResultScreen(game: String, navController: NavController, viewModel: AdminViewModel) {
    val results = viewModel.getResults(game).collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Text(text = game)
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(results.value) { result ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(24.dp))
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(result.team)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(result.result.toString())
                }
            }
        }
    }
}