package uz.suhrob.zakovat.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import uz.suhrob.zakovat.data.model.*

@ExperimentalCoroutinesApi
class FirestoreDataSourceImpl : FirestoreDataSource {
    private val firestore = FirebaseFirestore.getInstance()

    override fun getUserFlow(login: String): Flow<User> {
        return firestore.collection("users").document(login).getFirestoreFlow()
    }

    override fun addTeam(login: String, password: String) {
        firestore.collection("users").document(login).set(User(login, password))
    }

    override fun getTeamPlayers(team: String): Flow<List<Player>> {
        return firestore.collection("players").whereEqualTo("team", team).getFirestoreFlow()
    }

    override fun addNewPlayer(player: Player) {
        firestore.collection("players").add(player)
    }

    override fun getResultsByTeam(team: String): Flow<List<GameResult>> {
        return firestore.collection("results").whereEqualTo("team", team).getFirestoreFlow()
    }

    override fun getTeamsFlow(): Flow<List<User>> {
        return firestore.collection("users").whereNotEqualTo("login", "admin").getFirestoreFlow()
    }

    override fun getUpcomingGamesFlow(): Flow<List<Game>> {
        return firestore.collection("games").whereEqualTo("status", 0).getFirestoreFlow()
    }

    override fun getGamesFlow(): Flow<List<Game>> {
        return firestore.collection("games").whereEqualTo("status", -1).getFirestoreFlow()
    }

    override fun addUpcomingGame(game: Game) {
        firestore.collection("games").add(game)
    }

    override fun addNewQuestion(game: String, question: Question) {
        firestore.collection("questions").document().collection(game).add(question)
    }

    override fun addAnswer(game: String, answer: Answer) {
        firestore.collection("answers").document("${answer.question}|${answer.team}").set(answer)
    }

    override fun setAnswerRightOrNot(game: String, answer: Answer, isRight: Boolean) {
        firestore.collection("answers").document("${answer.question}|${answer.team}")
            .set(answer.copy(isRight = if (isRight) 1 else -1))
    }

    override fun setResults(results: List<GameResult>) {
        firestore.collection("results").add(results)
    }
}