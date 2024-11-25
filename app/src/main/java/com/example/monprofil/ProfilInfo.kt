package com.example.monprofil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Profilinfo(name: String, modifier: Modifier = Modifier, onNavigateToFilms: () -> Unit) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
        // Mode portrait
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.maceo),
                contentDescription = "Photo de profil Maceo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section des informations (LinkedIn, email, Instagram)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin_logo),
                        contentDescription = "logo linkedin",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "LinkedIn : @maceolinkedIn"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_email),
                        contentDescription = "logo email",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Email : maceo.dorigny@gmail.com"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_insta),
                        contentDescription = "logo insta",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Instagram : @maceolerigolo"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bouton de navigation vers Films
            Button(
                onClick = onNavigateToFilms,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Démarrer")
            }
        }
    } else {
        // Mode paysage
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(150.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.maceo),
                    contentDescription = "Photo de profil Maceo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.align(alignment = Alignment.CenterHorizontally),
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(100.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin_logo),
                        contentDescription = "logo linkedin",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "LinkedIn : @maceolinkedIn"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_email),
                        contentDescription = "logo email",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Email : maceo.dorigny@gmail.com"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_insta),
                        contentDescription = "logo insta",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Instagram : @maceolerigolo"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bouton de navigation vers Films
                Button(
                    onClick = onNavigateToFilms,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Démarrer")
                }
            }
        }
    }
}

