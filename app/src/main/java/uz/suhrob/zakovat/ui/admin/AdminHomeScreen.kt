package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uz.suhrob.zakovat.utils.dateToString

@Composable
fun AdminHomeScreen(navController: NavController, viewModel: AdminViewModel) {
    val teams = viewModel.teams.collectAsState()
    val upComingGames = viewModel.upcomingGames.collectAsState()
    val runningGames = viewModel.runningGames.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Zakovat") },
                backgroundColor = MaterialTheme.colors.primary,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Yangi o'yin") },
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                },
                onClick = { navController.navigate("new_game") }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(all = 16.dp)) {
            if (runningGames.value.isNotEmpty()) {
                item {
                    Text(text = "Davom etayotgan o'yinlar", style = MaterialTheme.typography.h5)
                }
                items(items = runningGames.value) { item ->
                    GameItem(title = item.title, time = item.time.dateToString()) {
                        navController.navigate("game/${item.title}")
                    }
                }
            }
            item {
                Text(text = "Kelayotgan o'yinlar", style = MaterialTheme.typography.h5)
            }
            if (upComingGames.value.isEmpty()) {
                item {
                    Text(text = "O'yinlar mavjud emas", style = MaterialTheme.typography.h5)
                }
            } else {
                items(items = upComingGames.value) { item ->
                    GameItem(title = item.title, time = item.time.dateToString()) {
                        navController.navigate("game_start/${item.title}")
                    }
                }
            }
            item {
                Text(
                    text = "Jamoalar",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.h5
                )
            }
            items(items = teams.value) { team ->
                TeamItem(title = team.login) {}
            }
        }
    }
}

@Composable
fun GameItem(title: String, time: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
        Text(text = time, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun TeamItem(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        style = MaterialTheme.typography.h6
    )
}