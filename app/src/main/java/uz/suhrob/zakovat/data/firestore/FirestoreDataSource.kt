package uz.suhrob.zakovat.data.firestore

import kotlinx.coroutines.flow.Flow
import uz.suhrob.zakovat.data.model.*

interface FirestoreDataSource {
    fun getUserFlow(login: String): Flow<User>
    fun addTeam(login: String, password: String)
    fun getTeamFlow(team: String): Flow<Team>
    fun addNewPlayer(name: String, imageUrl: String)
    fun getGamesByTeam(team: String): Flow<List<Game>>
    fun getTeamsFlow(): Flow<List<Team>>
    fun getUpcomingGamesFlow(): Flow<List<Game>>
    fun getQuestionTimeFlow(): Flow<Int>
    fun setQuestionTimeFlow(time: Int)
    fun getGamesFlow(): Flow<List<Game>>
    fun addUpcomingGame(game: Game)
    fun addNewQuestion(game: String, question: Question)
    fun addAnswer(game: String, answer: Answer)
    fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean)
}