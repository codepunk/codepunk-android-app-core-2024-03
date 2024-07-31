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
import com.codepunk.skeleton.ui.theme.SkeletonTheme

@Suppress("SpellCheckingInspection")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToMarillion: () -> Unit = {},
    onNavigateToTaylorSwift: () -> Unit = {}
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
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(
                onClick = {
                    onNavigateToMarillion()
                },
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                @Suppress("SpellCheckingInspection")
                Text("Marillion")
            }
            Button(
                onClick = {
                    onNavigateToTaylorSwift()
                },
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text("Taylor Swift")
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