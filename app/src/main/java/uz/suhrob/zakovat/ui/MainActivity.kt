package uz.suhrob.zakovat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uz.suhrob.zakovat.ui.theme.ZakovatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZakovatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NewGameScreen()
                }
            }
        }
    }
}
