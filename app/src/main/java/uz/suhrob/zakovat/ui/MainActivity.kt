package uz.suhrob.zakovat.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.zakovat.data.firestore.FirestoreDataSourceImpl
import uz.suhrob.zakovat.data.model.Game
import uz.suhrob.zakovat.data.pref.AppPrefs
import uz.suhrob.zakovat.data.repository.Repository
import uz.suhrob.zakovat.ui.admin.*
import uz.suhrob.zakovat.ui.login.*
import uz.suhrob.zakovat.ui.starter.StarterScreen
import uz.suhrob.zakovat.ui.team.*
import uz.suhrob.zakovat.ui.theme.ZakovatTheme

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    lateinit var pref : AppPrefs
    lateinit var repository : Repository

    private val authViewModel by viewModels<AuthViewModel>(factoryProducer = {AuthViewModelFactory(repository)})
    private val teamViewModel by viewModels<TeamViewModel>(factoryProducer = {TeamViewModelFactory(repository, pref)})
    private val adminViewModel by viewModels<AdminViewModel>(factoryProducer = {AdminViewModelFactory(repository)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = AppPrefs(applicationContext)
        repository = Repository(FirestoreDataSourceImpl(), pref)
        setContent {
            ZakovatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val startDestination = when {
                        pref.getUserType() == null -> "starter"
                        pref.getUserType() == "admin" -> "admin_home"
                        pref.getUserType() == "team" -> "team_home"
                        else -> ""
                    }
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination) {
                        composable("starter") {
                            StarterScreen(navController)
                        }
                        composable("admin_login") {
                            AdminLoginScreen(navController, pref, authViewModel) {toast(it)}
                        }
                        composable("team_login") {
                            TeamLoginScreen(navController, pref, authViewModel) {toast(it)}
                        }
                        composable("team_register") {
                            TeamRegisterScreen(navController, pref, authViewModel) {toast(it)}
                        }
                        composable("admin_home") {
                            AdminHomeScreen(navController, adminViewModel)
                        }
                        composable("team_home") {
                            TeamHomeScreen(navController,teamViewModel, pref)
                        }
                        composable("new_player") {
                            AddNewPlayerScreen(navController, teamViewModel, pref) {toast(it)}
                        }
                        composable("team_game/{title}", arguments = listOf(navArgument("title"){})) {
                            TeamGameScreen(title = it.arguments?.getString("title") ?: "", navController = navController, teamViewModel, pref)
                        }
                        composable("game/{title}", arguments = listOf(navArgument("title"){})) {
                            GameScreen(it.arguments?.getString("title") ?: "", navController, adminViewModel) {toast(it)}
                        }
                        composable("game_start/{title}", arguments = listOf(navArgument("title") {})) {
                            GameStartScreen(it.arguments?.getString("title") ?: "", navController, adminViewModel) {toast(it)}
                        }
                        composable("new_game") {
                            NewGameScreen(navController, adminViewModel) {toast(it)}
                        }
                        composable("result/{result}", arguments = listOf(navArgument("result") {})) {
                            GameResultScreen(
                                game = it.arguments?.getString("result") ?: "",
                                navController = navController,
                                viewModel = adminViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}
