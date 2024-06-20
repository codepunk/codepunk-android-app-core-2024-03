package com.codepunk.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.lifecycleScope
import com.codepunk.skeleton.core.loginator.Loginator
import com.codepunk.skeleton.data.local.dao.ArtistDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.util.parseElapsedTimeString
import com.codepunk.skeleton.util.toElapsedTimeString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

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

        val durations = listOf(
            DurationUnit.DAYS to 10.days + 5.hours + 13.minutes + 0.seconds + 246.milliseconds,
            DurationUnit.HOURS to 10.days + 5.hours + 13.minutes + 49.seconds + 246.milliseconds,
            DurationUnit.MINUTES to 1.hours + 4.seconds,
            DurationUnit.DAYS to 28.seconds,
            DurationUnit.HOURS to 1.hours + 10.milliseconds,
            DurationUnit.MINUTES to Duration.ZERO,
            DurationUnit.DAYS to Duration.ZERO,
            DurationUnit.DAYS to Duration.INFINITE,
            DurationUnit.HOURS to -Duration.INFINITE
        )
        val mapped = durations.map { (durationUnit, duration) ->
            duration.toElapsedTimeString(durationUnit)
        }
        val remapped = mapped.map {
            parseElapsedTimeString(it)
        }
        val x = "$durations $mapped $remapped"

        lifecycleScope.launch {
            @Suppress("SpellCheckingInspection")
            val amarok = discogsWebService.getMaster(44352)
            val misplacedChildhood = discogsWebService.getMaster(16191)
            @Suppress("Unused")
            Loginator.d { "$amarok $misplacedChildhood" }
        }

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
                        Loginator.d { "label = $label" }
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
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = modifier.height(8.dp))
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
