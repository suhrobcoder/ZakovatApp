package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val showDialog = mutableStateOf(false)
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Sozlamalar") }) }) {
        if (showDialog.value) {
            TimePickerDialog(time = 100, onChange = { /*TODO*/ }, onDismiss = {showDialog.value = false})
        }
        LazyColumn(modifier = Modifier.padding(vertical = 16.dp)) {
            item {
                SettingsTextItem(title = "Savol uchun ajratilgan vaqt", value = "3:00") {

                }
            }
        }
    }
}

@Composable
fun SettingsTextItem(title: String, value: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
        Text(text = value, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun TimePickerDialog(time: Int, onChange: (Int) -> Unit, onDismiss: () -> Unit) {
    val min = mutableStateOf(time / 60)
    val sec = mutableStateOf(time % 60)
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Row {
                TextButton(onClick = onDismiss) {
                    Text(text = "Bekor qilish")
                }
                Button(onClick = { onChange(time) }) {
                    Text(text = "Saqlash")
                }
            }
        },
        title = {
            Text(text = "Har bir savol uchun vaqt")
        },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = min.value.toString(),
                    onValueChange = { text -> min.value = text.toInt() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Text(text = ":")
                OutlinedTextField(
                    value = sec.value.toString(),
                    onValueChange = { text -> sec.value = text.toInt() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }
    )
}