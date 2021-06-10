package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.data.model.Game

@Composable
fun NewGameScreen(navController: NavController, viewModel: AdminViewModel, toast: (String) -> Unit) {
    var gameTitle by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
//    val dialog = remember {
//        MaterialDialog()
//    }
//    dialog.build {
//        datepicker {
//
//        }
//        buttons {
//
//        }
//    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = "Yangi o'yin") },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        if (showDialog) {

        }
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            OutlinedTextField(
                value = gameTitle,
                onValueChange = {gameTitle = it},
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
                Button(onClick = {
                    scope.launch {
                        try {
                            viewModel.newGame(Game(gameTitle))
                            navController.popBackStack()
                        } catch (e: Exception) {
                            toast("Xatolik")
                        }
                    }
                }) {
                    Text(text = "Qo'shish")
                }
            }
        }
    }
}