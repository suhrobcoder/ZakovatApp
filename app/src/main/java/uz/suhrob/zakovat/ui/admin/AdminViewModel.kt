package uz.suhrob.zakovat.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import uz.suhrob.zakovat.data.model.*
import uz.suhrob.zakovat.data.repository.Repository

@ExperimentalCoroutinesApi
class AdminViewModel(private val repository: Repository): ViewModel() {
    val teams = MutableStateFlow(listOf<User>())
    val upcomingGames = MutableStateFlow(listOf<Game>())
    val runningGames = MutableStateFlow(listOf<Game>())

    init {
        repository.getTeams()
            .onEach {
                teams.value = it
            }
            .launchIn(viewModelScope)
        repository.getUpcomingGames()
            .onEach {
                upcomingGames.value = it
            }
            .launchIn(viewModelScope)
        repository.getRunningGames()
            .onEach {
                runningGames.value = it
            }
            .launchIn(viewModelScope)
    }

    suspend fun newGame(game: Game) {
        repository.addUpcomingGame(game)
    }

    suspend fun startGame(game: Game) {
        repository.startGame(game)
    }

    fun getGame(title: String) = repository.getGame(title)

    fun setWrong(game: String, answer: Answer) = repository.setAnswerRightOrNot(game, answer, false)

    fun setRight(game: String, answer: Answer) = repository.setAnswerRightOrNot(game, answer, true)

    suspend fun addNewQuestion(game: String, question: Question) {
        repository.addNewQuestion(game, question)
    }

    suspend fun getTeamResult(game: String, team: String): Int = repository.getTeamResult(game, team)

    fun getQuestions(game: String) = repository.getQuestions(game)

    suspend fun setResult(game: String, results: List<GameResult>) = repository.setResults(game, results)

    fun getAnswers(game: String, question: String): Flow<List<Answer>> = repository.getAnswers(game, question)

    fun getResults(game: String): Flow<List<GameResult>> = repository.getResults(game)

    suspend fun stopGame(title: String) = repository.stopGame(title)
}