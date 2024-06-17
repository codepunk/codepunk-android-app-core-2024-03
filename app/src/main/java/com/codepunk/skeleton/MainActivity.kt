package com.codepunk.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.codepunk.skeleton.core.loginator.Loginator
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var discogsWebService: DiscogsWebService

    @Inject
    lateinit var artistDao: ArtistDao // TODO TEMP

    @Inject
    lateinit var discogsRepository: DiscogsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        name = "Scott",
                        onFetchData = ::fetchData
                    )
                }
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            discogsRepository.fetchArtist(218108).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = Throwable(it)) {
                            "fetchArtist encountered an error"
                        }
                    },
                    fb = {
                        Loginator.d { "artist = $it" }
                    },
                    fab = { th: Throwable, artist: Artist? ->
                        Loginator.e(throwable = Throwable(th)) {
                            "fetchArtist encountered an error"
                        }
                        Loginator.d { "artist = $artist" }
                    }
                )
            }
        }

        lifecycleScope.launch {
            discogsRepository.fetchLabel(1).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = Throwable(it)) {
                            "fetchLabel encountered an error"
                        }
                    },
                    fb = {
                        Loginator.d { "label = $it" }
                    },
                    fab = { th: Throwable, label: Label? ->
                        Loginator.e(throwable = Throwable(th)) {
                            "fetchLabel encountered an error"
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onFetchData: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        Button(
            onClick = { onFetchData() },
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text("Fetch Data")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkeletonTheme {
        Greeting("Android")
    }
}
