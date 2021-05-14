package uz.suhrob.zakovat.data.firestore

import kotlinx.coroutines.flow.Flow
import uz.suhrob.zakovat.data.model.*

interface FirestoreDataSource {
    fun getUserFlow(login: String): Flow<User>
    fun addTeam(login: String, password: String)
    fun getTeamPlayers(team: String): Flow<List<Player>>
    fun addNewPlayer(player: Player)
    fun getResultsByTeam(team: String): Flow<List<GameResult>>
    fun getTeamsFlow(): Flow<List<String>>
    fun getUpcomingGamesFlow(): Flow<List<Game>>
    fun getGamesFlow(): Flow<List<Game>>
    fun addUpcomingGame(game: Game)
    fun addNewQuestion(game: String, question: Question)
    fun addAnswer(game: String, answer: Answer)
    fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean)
    fun setResults(results: List<GameResult>)
}