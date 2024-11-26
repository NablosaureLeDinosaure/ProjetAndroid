package com.example.monprofil

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun ActeurScreen(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val actors = mainViewModel.actors.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Déclencher la récupération des acteurs
    LaunchedEffect(Unit) {
        mainViewModel.fetchPopularActors()
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
                        mainViewModel.searchActors(query)
                    },
                    label = { Text("Rechercher un acteur") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Titre de la page
                Text(
                    text = "Liste des acteurs",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )

                // Grille des acteurs
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 colonnes
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(actors.value) { actor ->
                        ActorItemGrid(actor)
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
                            mainViewModel.searchActors(query)
                        },
                        label = { Text("Rechercher un acteur") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    // Grille des acteurs
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3), // 3 colonnes
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(actors.value) { actor ->
                            ActorItemGrid(actor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActorItemGrid(actor: LastActeurData) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
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
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://image.tmdb.org/t/p/w500${actor.profile_path}")
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Actor Profile",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = actor.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
