package uz.suhrob.zakovat.data.firestore

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import uz.suhrob.zakovat.data.model.*
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class FirestoreDataSourceImpl {
    private val firestore = FirebaseFirestore.getInstance()

    fun getUserFlow(login: String): Flow<User> {
        return firestore.collection("users").document(login).getFirestoreFlow()
    }

    suspend fun addTeam(login: String, password: String) {
        firestore.collection("users").document(login).set(User(login, password)).await()
    }

    fun getTeamPlayers(team: String): Flow<List<Player>> {
        return firestore.collection("players").whereEqualTo("team", team).getFirestoreFlow()
    }

    suspend fun addNewPlayer(player: Player) {
        firestore.collection("players").add(player).await()
    }

    fun getResultsByTeam(team: String): Flow<List<GameResult>> {
        return firestore.collection("results").whereEqualTo("team", team).getFirestoreFlow()
    }

    fun getTeamsFlow(): Flow<List<User>> {
        return firestore.collection("users").whereNotEqualTo("login", "admin").getFirestoreFlow()
    }

    fun getRunningGames(): Flow<List<Game>> {
        return firestore.collection("games").whereEqualTo("status", 1).getFirestoreFlow()
    }

    fun getUpcomingGamesFlow(): Flow<List<Game>> {
        return firestore.collection("games").whereEqualTo("status", 0).getFirestoreFlow()
    }

    fun getGame(title: String): Flow<Game> {
        return firestore.collection("games").document(title).getFirestoreFlow()
    }

    fun getGamesFlow(): Flow<List<Game>> {
        return firestore.collection("games").whereEqualTo("status", -1).getFirestoreFlow()
    }

    suspend fun addUpcomingGame(game: Game) {
        firestore.collection("games").document(game.title).set(game).await()
    }

    suspend fun startGame(game: Game) {
        firestore.collection("games").document(game.title).set(game.copy(status = 1))
    }

    suspend fun addNewQuestion(game: String, question: Question) {
        firestore.collection("questions").document(game).collection(game).add(question).await()
    }

    fun getQuestions(game:String): Flow<List<Question>> {
        return firestore.collection("questions").document(game).collection(game).getFirestoreFlow()
    }

    fun addAnswer(game: String, answer: Answer) {
        Log.d("AppDebug", answer.toString())
        firestore.collection("answers").document(game).collection(answer.question).document(answer.team).set(answer)
    }

    fun getAnswers(game: String, question: String): Flow<List<Answer>> {
        return firestore.collection("answers").document(game).collection(question).getFirestoreFlow()
    }

    fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean) {
        firestore.collection("answers").document(game).collection(answer.question).document(answer.team)
            .set(answer.copy(right = if (isRight) 1 else -1))
    }

    fun getAnswerByQuestion(game: String, question: String, team: String): Flow<Answer> {
        return firestore.collection("answers").document(game).collection(question).document(team).getFirestoreFlow()
    }

    suspend fun getTeamResult(game: String, team: String): Int {
        val questions = firestore.collection("questions").document(game).collection(game).getFirestoreFlow<Question>().first()
        var score = 0
        for (question in questions) {
            val answer = firestore.collection("answers").document(game).collection(question.title).document(team).get().await()
            if (answer.exists()) {
                if (answer["right"].toString() == "1") {
                    score++
                }
            }
        }
        return score
    }

    suspend fun setResults(game: String, results: List<GameResult>) {
        Log.d("AppDebug", "setting results")
        for (result in results) {
            firestore.collection("results").document("games").collection(game).document(result.team).set(result).await()
        }
    }

    fun getResults(game: String): Flow<List<GameResult>> {
        return firestore.collection("results").document("games").collection(game).getFirestoreFlow()
    }

    suspend fun stopGame(title: String) {
        val game = getGame(title).first()
        firestore.collection("games").document(title).set(game.copy(status = -1)).await()
    }
}