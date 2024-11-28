package com.example.monprofil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monprofil.ui.theme.MonProfilTheme

@Composable
fun Musique(mainViewModel: MainViewModel, playlist: Playlist?) {
    MonProfilTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                if (playlist != null) {
                    Text(
                        text = playlist.title,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                if (playlist != null) {
                    Text(
                        text = "Créée par ${playlist.creator.name}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = "file:///android_asset/image/1.jpg",
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}


