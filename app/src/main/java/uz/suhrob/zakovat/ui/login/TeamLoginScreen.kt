package uz.suhrob.zakovat.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.R
import uz.suhrob.zakovat.data.pref.AppPrefs

@Composable
fun TeamLoginScreen(navController: NavController, pref: AppPrefs, viewModel: AuthViewModel, toast: (String) -> Unit) {
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.group_avatar),
            contentDescription = null,
            modifier = Modifier.size(144.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = login,
            onValueChange = {login = it},
            placeholder = { Text(text = "Guruh nomi") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Parol") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            scope.launch {
                val result = viewModel.auth(login, password)
                if (result) {
                    pref.setUserType("team")
                    pref.setGroupName(login)
                    navController.popBackStack()
                    navController.navigate("team_home")
                } else {
                    toast("Guruh nomi yoki parol noto'g'ri")
                }
            }
        }) {
            Text(
                text = "Kirish",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "Guruhingiz yo'qmi")
            Text(
                text = "Yangi guruh",
                style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate("team_register")
                    },
                    interactionSource = MutableInteractionSource(),
                    indication = null
                )
            )
        }
    }
}