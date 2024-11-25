package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.monprofil.ui.theme.MonProfilTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movie: LastMovieData, navController: NavHostController) {
    MonProfilTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Movie Details", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_retour), // Utilise une belle image pour Films
                                contentDescription = "Films",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(45.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Section avec l'image en en-tête
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movie.backdrop_path}"),
                    contentDescription = "Movie Backdrop",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Titre principal et date de sortie
                Text(
                    text = movie.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Affichage de l'affiche
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(end = 16.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = movie.release_date,
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "language : " + movie.original_language,
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section Synopsis
                Text(
                    text = "Synopsis",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = movie.overview,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
