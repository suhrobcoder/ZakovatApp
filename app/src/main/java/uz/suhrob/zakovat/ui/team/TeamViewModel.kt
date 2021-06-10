package uz.suhrob.zakovat.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.suhrob.zakovat.data.model.Answer
import uz.suhrob.zakovat.data.model.Game
import uz.suhrob.zakovat.data.model.Player
import uz.suhrob.zakovat.data.pref.AppPrefs
import uz.suhrob.zakovat.data.repository.Repository

class TeamViewModel(private val repository: Repository, private val pref: AppPrefs) : ViewModel() {
    val players = MutableStateFlow(listOf<Player>())
    val runningGames = MutableStateFlow(listOf<Game>())

    init {
        repository.getTeamFLow(pref.getGroupName() ?: "")
            .onEach {
                players.value = it
            }
            .launchIn(viewModelScope)
        repository.getRunningGames()
            .onEach {
                runningGames.value = it
            }
            .launchIn(viewModelScope)
    }

    suspend fun addNewPlayer(player: Player) {
        repository.addNewPlayer(player)
    }

    suspend fun addAnswer(game: String, answer: Answer) {
        repository.addAnswer(game, answer)
    }

    fun getQuestions(game: String) = repository.getQuestions(game)

    fun getAnswerByQuestion(game: String, question: String, team: String) = repository.getAnswerByQuestion(game, question, team)
}