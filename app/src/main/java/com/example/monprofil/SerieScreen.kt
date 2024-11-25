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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.monprofil.ui.theme.MonProfilTheme

@Composable
fun SeriesScreen(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val tvShows = mainViewModel.tvShows.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Déclencher la récupération des séries
    LaunchedEffect(Unit) {
        mainViewModel.fetchLatestTvShows()
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
                        mainViewModel.searchTvShows(query)
                    },
                    label = { Text("Rechercher une série") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Titre de la page
                Text(
                    text = "Liste des séries",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )

                // Grille des séries
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 colonnes
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tvShows.value) { tvShow ->
                        TvItemGrid(tvShow, navController)
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
                            mainViewModel.searchTvShows(query)
                        },
                        label = { Text("Rechercher une série") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    // Grille des séries
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3), // 3 colonnes
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tvShows.value) { tvShow ->
                            TvItemGrid(tvShow, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TvItemGrid(tvShow: LastTvData, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("tvDetail/${tvShow.id}") }, // Navigue vers les détails de la série
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6200EE) // Fond gris clair pour une apparence élégante
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://image.tmdb.org/t/p/w500${tvShow.poster_path}")
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "TV Show Poster",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = tvShow.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "First Air Date: ${tvShow.first_air_date}",
                fontSize = 14.sp,
                color = Color.LightGray // Texte de couleur grise pour plus de cohérence
            )
        }
    }
}
