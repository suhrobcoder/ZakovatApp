package uz.suhrob.zakovat.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import uz.suhrob.zakovat.data.firestore.FirestoreDataSourceImpl
import uz.suhrob.zakovat.data.model.*
import uz.suhrob.zakovat.data.pref.AppPrefs
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(
    private val dataSource: FirestoreDataSourceImpl,
    private val pref: AppPrefs,
) {
    suspend fun authenticateUser(user: User): Boolean {
        val user1 = dataSource.getUserFlow(user.login).first()
        return user1.password == user.password
    }

    suspend fun addTeam(login: String, password: String) {
        dataSource.addTeam(login, password)
    }

    fun getTeamFLow(team: String): Flow<List<Player>> {
        return dataSource.getTeamPlayers(team)
    }

    suspend fun addNewPlayer(player: Player) {
        dataSource.addNewPlayer(player)
    }

    fun getGamesByTeam(team: String): Flow<List<GameResult>> {
        return dataSource.getResultsByTeam(team)
    }

    fun getTeams(): Flow<List<User>> {
        return dataSource.getTeamsFlow()
    }

    fun getUpcomingGames(): Flow<List<Game>> {
        return dataSource.getUpcomingGamesFlow()
    }

    fun getGamesFlow(): Flow<List<Game>> {
        return dataSource.getGamesFlow()
    }

    suspend fun addUpcomingGame(game: Game) {
        dataSource.addUpcomingGame(game)
    }

    fun getRunningGames(): Flow<List<Game>> {
        return dataSource.getRunningGames()
    }

    fun getGame(title: String): Flow<Game> {
        return dataSource.getGame(title)
    }

    suspend fun startGame(game: Game) {
        dataSource.startGame(game)
    }

    suspend fun addNewQuestion(game: String, question: Question) {
        dataSource.addNewQuestion(game, question)
    }

    fun getQuestions(game: String): Flow<List<Question>> = dataSource.getQuestions(game)

    fun addAnswer(game: String, answer: Answer) {
        dataSource.addAnswer(game, answer)
    }

    fun getAnswers(game: String, question: String): Flow<List<Answer>> = dataSource.getAnswers(game, question)

    fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean) {
        dataSource.setAnswerRightOrNot(game, answer, isRight)
    }

    fun getAnswerByQuestion(game: String, question: String, team: String): Flow<Answer> = dataSource.getAnswerByQuestion(game, question, team)

    suspend fun setResults(game: String, results: List<GameResult>) {
        dataSource.setResults(game, results)
    }

    suspend fun getTeamResult(game: String, team: String): Int = dataSource.getTeamResult(game, team)

    fun getResults(game: String): Flow<List<GameResult>> = dataSource.getResults(game)

    suspend fun stopGame(title: String) = dataSource.stopGame(title)
}