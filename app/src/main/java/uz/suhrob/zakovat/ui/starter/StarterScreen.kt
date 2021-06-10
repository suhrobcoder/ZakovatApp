package uz.suhrob.zakovat.ui.starter

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uz.suhrob.zakovat.data.pref.AppPrefs

@Composable
fun StarterScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.popBackStack()
            navController.navigate("admin_login")
        }) {
            Text(
                text = "Admin bo'lib kirish",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = {
            navController.popBackStack()
            navController.navigate("team_login")
        }) {
            Text(
                text = "Guruh bo'lib kirish",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}