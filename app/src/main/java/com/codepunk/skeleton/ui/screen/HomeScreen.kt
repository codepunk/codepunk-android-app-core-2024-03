package com.codepunk.skeleton.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.ui.AN_HOUR_BEFORE_MASTER
import com.codepunk.skeleton.ui.ATLANTIC_RECORDS
import com.codepunk.skeleton.ui.MARILLION
import com.codepunk.skeleton.ui.REPUBLIC_RECORDS
import com.codepunk.skeleton.ui.TAYLOR_SWIFT
import com.codepunk.skeleton.ui.TORTURED_POETS_MASTER
import com.codepunk.skeleton.ui.UNION_RELEASE
import com.codepunk.skeleton.ui.theme.SkeletonTheme

@Suppress("SpellCheckingInspection")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToArtist: (Long) -> Unit = {},
    onNavigateToLabel: (Long) -> Unit = {},
    onNavigateToMaster: (Long) -> Unit = {},
    onNavigateToRelease: (Long) -> Unit = {}
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello Codepunk!",
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(
                onClick = { onNavigateToArtist(MARILLION) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                @Suppress("SpellCheckingInspection")
                Text("Marillion")
            }
            Button(
                onClick = { onNavigateToArtist(TAYLOR_SWIFT) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Taylor Swift")
            }
            Button(
                onClick = { onNavigateToLabel(ATLANTIC_RECORDS) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Atlantic")
            }
            Button(
                onClick = { onNavigateToLabel(REPUBLIC_RECORDS) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Republic")
            }
            Button(
                onClick = { onNavigateToMaster(TORTURED_POETS_MASTER) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Tortured Poets… (Master)")
            }
            Button(
                onClick = { onNavigateToMaster(AN_HOUR_BEFORE_MASTER) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("An Hour Before… (Master)")
            }
            Button(
                onClick = { onNavigateToRelease(UNION_RELEASE) },
                modifier = modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Union (Release)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SkeletonTheme(darkTheme = true) {
        HomeScreen()
    }
}