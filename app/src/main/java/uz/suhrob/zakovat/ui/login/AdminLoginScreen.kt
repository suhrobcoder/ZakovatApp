package uz.suhrob.zakovat.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.R
import uz.suhrob.zakovat.data.pref.AppPrefs

@Composable
fun AdminLoginScreen(navController: NavController, pref: AppPrefs, viewModel: AuthViewModel, toast: (String) -> Unit) {
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
            bitmap = ImageBitmap.imageResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier.size(144.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            placeholder = { Text(text = "Login") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Parol") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            scope.launch {
                val result = viewModel.auth(login, password)
                if (result) {
                    pref.setUserType("admin")
                    navController.popBackStack()
                    navController.navigate("admin_home")
                } else {
                    toast("Login yoki parol noto'g'ri")
                }
            }
        }) {
            Text(
                text = "Kirish",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}