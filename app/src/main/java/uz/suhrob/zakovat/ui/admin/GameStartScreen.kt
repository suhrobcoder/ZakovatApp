package uz.suhrob.zakovat.ui.admin

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.data.model.Game
import uz.suhrob.zakovat.utils.dateToString

@Composable
fun GameStartScreen(title: String, navController: NavController, viewModel: AdminViewModel, toast: (String) -> Unit) {
    val scope = rememberCoroutineScope()
    val game by viewModel.getGame(title).collectAsState(initial = Game())
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = title) },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Text(text = "O'yin vaqt: ${game.time.dateToString()}")
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = {
                    scope.launch {
                        try {
                            viewModel.startGame(game)
                            navController.popBackStack()
                            navController.navigate("game/${game.title}")
                        } catch (e: Exception) {
                            toast("Xatolik")
                            Log.d("AppDebug", e.message.toString())
                        }
                    }
                }) {
                    Text(text = "O'yinni boshlash")
                }
            }
        }
    }
}