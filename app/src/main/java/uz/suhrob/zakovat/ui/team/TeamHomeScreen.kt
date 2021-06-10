package uz.suhrob.zakovat.ui.team

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import uz.suhrob.zakovat.R
import uz.suhrob.zakovat.data.pref.AppPrefs
import uz.suhrob.zakovat.ui.admin.GameItem
import uz.suhrob.zakovat.ui.theme.gray900

@Composable
fun TeamHomeScreen(navController: NavController, viewModel: TeamViewModel, pref: AppPrefs) {
    val players = viewModel.players.collectAsState()
    val runningGames = viewModel.runningGames.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = pref.getGroupName() ?: "") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        LazyColumn {
            if (runningGames.value.isNotEmpty()) {
                item {
                    Text("Boshlangan o'yinlar", style = MaterialTheme.typography.h4)
                }
                items(runningGames.value) { game ->
                    GameItem(title = game.title,) {
                        navController.navigate("team_game/${game.title}")
                    }
                }
            }
            items(players.value) { player ->
                PlayerItem(name = player.name, imageUrl = player.avatarUrl) {

                }
            }
            item {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { navController.navigate("new_player") }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                        Text(text = "Yangi a'zo qo'shish")
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(name: String, imageUrl: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(width = 1.dp, color = gray900, shape = RoundedCornerShape(size = 16.dp))
            .clickable { Log.d("AppDebug", "yes") }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberCoilPainter(
                request = imageUrl,
                previewPlaceholder = R.drawable.avatar
            ),
            contentDescription = null,
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = name, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun GameItem(title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
    }
}