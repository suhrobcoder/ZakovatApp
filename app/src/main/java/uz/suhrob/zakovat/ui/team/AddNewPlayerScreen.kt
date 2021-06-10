package uz.suhrob.zakovat.ui.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.data.model.Player
import uz.suhrob.zakovat.data.pref.AppPrefs

@Composable
fun AddNewPlayerScreen(navController: NavController, viewModel: TeamViewModel, pref: AppPrefs, toast: (String) -> Unit) {
    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Yangi a'zo qo'shish") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, end = 16.dp, start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberCoilPainter(request = ""),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Galereya orqali")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Kamera orqali")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = name, onValueChange = {name = it}, placeholder = { Text(text = "Ismi") })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = phone, onValueChange = {phone = it}, placeholder = { Text(text = "Telefon Raqam") })
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                val player = Player(pref.getGroupName() ?: "", name, "", phone)
                scope.launch {
                    try {
                        viewModel.addNewPlayer(player)
                        navController.popBackStack()
                    } catch (e: Exception) {
                        toast("Xatolik")
                    }
                }
                navController.popBackStack()
            }) {
                Text(text = "Qo'shish")
            }
        }
    }
}