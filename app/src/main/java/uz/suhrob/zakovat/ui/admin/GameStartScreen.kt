package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.suhrob.zakovat.data.model.Game
import uz.suhrob.zakovat.utils.dateToString

@Composable
fun GameStartScreen(game: Game) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = game.title) },
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
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "O'yinni boshlash")
                }
            }
        }
    }
}