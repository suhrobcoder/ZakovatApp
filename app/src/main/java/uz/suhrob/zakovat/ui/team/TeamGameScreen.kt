package uz.suhrob.zakovat.ui.team

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.data.model.Answer
import uz.suhrob.zakovat.data.model.Question
import uz.suhrob.zakovat.data.pref.AppPrefs

@Composable
fun TeamGameScreen(
    title: String,
    navController: NavController,
    viewModel: TeamViewModel,
    pref: AppPrefs
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var currentQuestion = remember { mutableStateOf(Question()) }
    var questions = viewModel.getQuestions(title).collectAsState(initial = listOf())
    val scope = rememberCoroutineScope()
    if (showDialog) {
        AddAnswerDialog(onDismiss = { showDialog = false }) {
            scope.launch {
                viewModel.addAnswer(
                    title,
                    Answer(currentQuestion.value.title, title, pref.getGroupName() ?: "", it)
                )
                showDialog = false
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }, title = { Text(text = title) }, backgroundColor = MaterialTheme.colors.primary)
        },

        ) {
        LazyColumn {
            items(questions.value) { question ->
                val answer = viewModel.getAnswerByQuestion(title, question.title, pref.getGroupName() ?: "")
                        .collectAsState(initial = Answer())
                QuestionItem(title = question.title, yourAnswer = answer.value) {
                    currentQuestion.value = question
                    showDialog = true
                }
            }
        }
    }
}

@Composable
fun AddAnswerDialog(answer: String = "", onDismiss: () -> Unit, onDone: (String) -> Unit) {
    var text by remember { mutableStateOf(answer) }
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
                placeholder = { Text(text = "Javob") })
        }
    )
}

@Composable
fun QuestionItem(title: String, yourAnswer: Answer, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
        if (yourAnswer.answer.isNotEmpty()) {
            Text(text = yourAnswer.answer, style = MaterialTheme.typography.subtitle1)
        }
    }
}