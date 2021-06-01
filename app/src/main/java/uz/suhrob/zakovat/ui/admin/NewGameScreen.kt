package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewGameScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = "Yangi o'yin") },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "O'yin nomi") },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "O'yin vaqti:  ")
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Tanlash")
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Qo'shish")
                }
            }
        }
    }
}