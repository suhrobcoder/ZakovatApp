package uz.suhrob.zakovat.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import uz.suhrob.zakovat.data.firestore.FirestoreDataSource
import uz.suhrob.zakovat.data.model.*
import uz.suhrob.zakovat.data.pref.AppPrefs
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: FirestoreDataSource,
    private val pref: AppPrefs,
) {
    suspend fun authenticateUser(user: User): Boolean {
        val user1 = dataSource.getUserFlow(user.login).first()
        return user1.password == user.password
    }

    fun addTeam(login: String, password: String) {
        dataSource.addTeam(login, password)
    }

    fun getTeamFLow(team: String): Flow<List<Player>> {
        return dataSource.getTeamPlayers(team)
    }

    fun addNewPlayer(player: Player) {
        dataSource.addNewPlayer(player)
    }

    fun getGamesByTeam(team: String): Flow<List<GameResult>> {
        return dataSource.getResultsByTeam(team)
    }

    fun getTeams(): Flow<List<String>> {
        return dataSource.getTeamsFlow()
    }

    fun getUpcomingGames(): Flow<List<Game>> {
        return dataSource.getUpcomingGamesFlow()
    }

    fun getGamesFlow(): Flow<List<Game>> {
        return dataSource.getGamesFlow()
    }

    fun addUpcomingGame(game: Game) {
        dataSource.addUpcomingGame(game)
    }

    fun addNewQuestion(game: String, question: Question) {
        dataSource.addNewQuestion(game, question)
    }

    fun addAnswer(game: String, answer: Answer) {
        dataSource.addAnswer(game, answer)
    }

    fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean) {
        dataSource.setAnswerRightOrNot(game, answer, isRight)
    }

    fun setResults(results: List<GameResult>) {
        dataSource.setResults(results)
    }
}