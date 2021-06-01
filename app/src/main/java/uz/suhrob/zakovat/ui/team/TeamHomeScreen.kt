package uz.suhrob.zakovat.ui.team

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import uz.suhrob.zakovat.R
import uz.suhrob.zakovat.ui.theme.gray900

@Composable
fun TeamHomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Guruh nomi") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ) {
        LazyColumn {
            items(15) {
                PlayerItem(
                    name = "Player",
                    imageUrl = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
                ) {}
            }
            item {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                        Text(text = "Yangi a'zo qo'shish")
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerItem(name: String, imageUrl: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(width = 1.dp, color = gray900, shape = RoundedCornerShape(size = 16.dp))
            .clickable { Log.d("AppDebug", "yes") }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberCoilPainter(
                request = imageUrl,
                previewPlaceholder = R.drawable.avatar
            ),
            contentDescription = null,
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = name, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun Preview_PlayerItem() {
    PlayerItem(
        name = "Player",
        imageUrl = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
    ) {}
}

@Preview
@Composable
fun Preview_TeamHomeScreen() {
    TeamHomeScreen()
}