package uz.suhrob.zakovat.ui.admin

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.R
import uz.suhrob.zakovat.data.model.Answer
import uz.suhrob.zakovat.data.model.GameResult
import uz.suhrob.zakovat.data.model.Question

@Composable
fun GameScreen(
    title: String,
    navController: NavController,
    viewModel: AdminViewModel,
    toast: (String) -> Unit
) {
    print(1)
    var showDialog by remember {
        mutableStateOf(false)
    }
    val questions = viewModel.getQuestions(title).collectAsState(initial = listOf())
    val scope = rememberCoroutineScope()
    if (showDialog) {
        NewQuestionDialog(onDismiss = { showDialog = false }) {
            scope.launch {
                try {
                    viewModel.addNewQuestion(title, Question(it))
                    showDialog = false
                } catch (e: Exception) {
                    toast("Xatolik")
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = title) },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            val teams = viewModel.teams.value
                            val results = ArrayList<GameResult>()
                            for (team in teams) {
                                val score = viewModel.getTeamResult(title, team.login)
                                results.add(GameResult(title, team.login, score))
                            }
                            // TODO o'rinlarni hisobla
                            viewModel.setResult(title, results)
                            viewModel.stopGame(title)
                            navController.popBackStack()
                            navController.navigate("result/$title")
                        }
                    }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_stop), contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Yangi savol") },
                onClick = { showDialog = true },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) })
        }
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            items(questions.value) { question ->
                val answers =
                    viewModel.getAnswers(title, question.title).collectAsState(initial = listOf())
                RunningGameItem(title, question = question, answers = answers.value, viewModel)
            }
        }
    }
}

@Composable
fun RunningGameItem(game: String, question: Question, answers: List<Answer>, viewModel: AdminViewModel) {
    var open by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = question.title, style = MaterialTheme.typography.h5)
                IconButton(onClick = { open = !open }) {
                    Icon(
                        imageVector = if (open) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                    )
                }
            }
            if (open) {
                Column {
                    answers.forEach { answer ->
                        TeamAnswerItem(
                            team = answer.team,
                            answer = answer.answer,
                            isRight = answer.right,
                            onRightClick = { viewModel.setRight(game, answer) },
                            onWrongClick = { viewModel.setWrong(game, answer) })
                    }
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
            Text(text = team, style = MaterialTheme.typography.h6)
            Text(text = answer, style = MaterialTheme.typography.subtitle1)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onRightClick() },
            modifier = Modifier.border(width = 1.dp, color = if (isRight == 1) Color.Black else Color.Gray, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.Green
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = { onWrongClick() },
            modifier = Modifier.border(width = 1.dp, color = if (isRight == -1) Color.Black else Color.Gray, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@Composable
fun NewQuestionDialog(onDismiss: () -> Unit, onDone: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = { onDismiss() }) {
                    Text("Bekor qilish")
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { onDone(text) }) {
                    Text("OK")
                }
            }
        },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(text = "Savol") })
        }
    )
}