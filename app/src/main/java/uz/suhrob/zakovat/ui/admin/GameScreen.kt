package uz.suhrob.zakovat.ui.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uz.suhrob.zakovat.data.model.Game

@Composable
fun GameScreen(game: Game) {
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
        }
    }
}

@Composable
fun RunningGameItem() {
    var open by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Savol")
                IconButton(onClick = { open = !open }) {
                    Icon(
                        imageVector = if (open) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                    )
                }
            }
            if (open) {
                LazyColumn {

                }
            }
        }
    }
}

@Composable
fun TeamAnswerItem(
    team: String,
    answer: String,
    isRight: Int,
    onRightClick: () -> Unit,
    onWrongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(24.dp))
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = team, style = MaterialTheme.typography.h4)
            Text(text = answer, style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.border(width = 1.dp, color = Color.Red, shape = CircleShape)
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = null, tint = Color.Green.copy(alpha = if (isRight == -1) 0.5f else 1f))
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.border(width = 1.dp, color = Color.Red, shape = CircleShape)
        ) {
            Icon(imageVector = Icons.Filled.Clear, contentDescription = null, tint = Color.Red.copy(alpha = if (isRight == 1) 0.5f else 1f))
        }
    }
}