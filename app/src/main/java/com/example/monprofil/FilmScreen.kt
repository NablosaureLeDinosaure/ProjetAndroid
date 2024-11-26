package com.example.monprofil

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.monprofil.ui.theme.MonProfilTheme

@Composable
fun FilmsScreen(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val movies = mainViewModel.movies.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Déclencher la récupération des films
    LaunchedEffect(Unit) {
        mainViewModel.fetchLatestMovies()
    }

    MonProfilTheme {
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Mode portrait
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Barre de recherche
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        mainViewModel.searchMovies(query)
                    },
                    label = { Text("Rechercher un film") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Titre de la page
                Text(
                    text = "Liste des films",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )

                // Grille des films
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 colonnes
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(movies.value) { movie ->
                        MovieItemGrid(movie, navController)
                    }
                }
            }
        } else {
            // Mode paysage
            Row(modifier = Modifier.fillMaxSize()) {
                // Barre de navigation verticale
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavigationItem("Films", R.drawable.video_player, navController, "film")
                    NavigationItem("Acteurs", R.drawable.actor, navController, "acteur")
                    NavigationItem("Séries", R.drawable.television, navController, "serie")
                }

                // Contenu principal
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    // Barre de recherche
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            mainViewModel.searchMovies(query)
                        },
                        label = { Text("Rechercher un film") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    // Grille des films
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(movies.value) { movie ->
                            MovieItemGrid(movie, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationItem(
    label: String,
    iconRes: Int,
    navController: NavHostController,
    route: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(route) }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun MovieItemGrid(movie: LastMovieData, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("movieDetail/${movie.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6200EE)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Affiche du film
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .apply { crossfade(true) }
                        .build()
                ),
                contentDescription = "Affiche du film",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            // Titre du film
            Text(
                text = movie.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            // Date de sortie
            Text(
                text = "Sortie : ${movie.release_date}",
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}
