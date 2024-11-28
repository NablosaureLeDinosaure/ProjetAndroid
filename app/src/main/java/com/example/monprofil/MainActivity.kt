package com.example.monprofil

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.monprofil.ui.theme.MonProfilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonProfilTheme {
                val navController = rememberNavController()
                val mainViewModel = viewModel<MainViewModel>()
                val configuration = LocalConfiguration.current

                Scaffold(
                    bottomBar = {
                        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            BottomNavigationBar(navController)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "profile",
        modifier = modifier
    ) {
        composable("profile") {
            Profilinfo(
                name = "Maceo Dorigny",
                onNavigateToFilms = { navController.navigate("musique") }
            )
        }
        composable("musique") {
            Musique()
        }
        composable("film") {
            FilmsScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable("acteur") {
            ActeurScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable("serie") {
            SeriesScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable("movieDetail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            if (movieId != null) {
                val movie = mainViewModel.getMovieById(movieId)
                if (movie != null) {
                    MovieDetailScreen(movie = movie, navController = navController)
                } else {
                    Text("Film non trouvé", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                Text("ID de film invalide", style = MaterialTheme.typography.bodyLarge)
            }
        }
        composable("tvDetail/{tvShowId}") { backStackEntry ->
            val tvShowId = backStackEntry.arguments?.getString("tvShowId")
            if (tvShowId != null) {
                val tvShow = mainViewModel.getTvShowById(tvShowId)
                if (tvShow != null) {
                    SerieDetailScreen(tvShow = tvShow, navController = navController)
                } else {
                    Text("Série non trouvée", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                Text("ID de série invalide", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute in listOf("film", "acteur", "serie", "musique")) {
        NavigationBar {
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.video_player),
                        contentDescription = "Films",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                },
                label = { Text("Films") },
                selected = currentRoute == "film",
                onClick = { navController.navigate("film") }
            )
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.actor),
                        contentDescription = "Acteurs",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                },
                label = { Text("Acteurs") },
                selected = currentRoute == "acteur",
                onClick = { navController.navigate("acteur") }
            )
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.television),
                        contentDescription = "Séries",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                },
                label = { Text("Séries") },
                selected = currentRoute == "serie",
                onClick = { navController.navigate("serie") }
            )
            NavigationBarItem(icon = {
                    AsyncImage(
                        model = "file:///android_asset/images/cover.jpg",
                        contentDescription = "",)
                }, label = { Text("Musique") }, selected = currentRoute == "musique", onClick = { navController.navigate("musique") })
        }
    }
}
