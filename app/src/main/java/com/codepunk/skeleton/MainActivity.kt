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
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebserviceV2
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.domain.repository.DiscogsRepositoryV2
import com.codepunk.skeleton.domainv2.model.Artist
import com.codepunk.skeleton.domainv2.model.Label
import com.codepunk.skeleton.domainv2.model.Master
import com.codepunk.skeleton.domainv2.model.Track
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
    lateinit var discogsWebserviceV2: DiscogsWebserviceV2

    @Inject
    lateinit var discogsRepository: DiscogsRepository

    @Inject
    lateinit var discogsRepositoryV2: DiscogsRepositoryV2

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
                        onFetchData = ::testStuff
                    )
                }
            }
        }
    }

    private fun testStuff() {
        /*
        testElapsedTimeString()
        testFetchArtist(TAYLOR_SWIFT)
        testFetchLabel(REPUBLIC_RECORDS)
        testFetchMaster(THE_TORTURED_POETS_DEPARTMENT)
        testFetchRelease(THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY)
         */

        //testFetchArtist(TAYLOR_SWIFT)
        //testFetchLabel(REPUBLIC_RECORDS)
        //testFetchLabel(ATLANTIC_RECORDS)
        //testFetchMaster(AN_HOUR_BEFORE_ITS_DARK_MASTER)
        testFetchMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)

        /*
        testFetchMaster(THE_TORTURED_POETS_DEPARTMENT)
        testFetchRelease(THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY)
         */
    }

    @Suppress("Unused")
    private fun testElapsedTimeString() {
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
        }.toMutableList().apply {
            add("")
        }.toList()
        val remapped = mapped.map {
            try {
                parseElapsedTimeString(it)
            } catch (e: IllegalArgumentException) {
                Duration.ZERO
            }
        }
        val x = "$durations $mapped $remapped"
        Loginator.d { x }
    }

    /*
    @Suppress("Unused")
    private fun testFetchArtist(artistId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchArtist(artistId).collect { result ->
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
    }

    private fun testFetchLabel(labelId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchLabel(labelId).collect { result ->
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

    private fun testFetchMaster(masterId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchMaster(masterId).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = Throwable(it)) {
                            "fetchMaster encountered an error"
                        }
                    },
                    fb = {
                        Loginator.d { "master = $it" }
                    },
                    fab = { th: Throwable, master: Master? ->
                        Loginator.e(throwable = Throwable(th)) {
                            "fetchMaster encountered an error"
                        }
                        Loginator.d { "master = $master" }
                    }
                )
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchRelease(releaseId: Long) {
        val x = "$releaseId"
    }
     */

    @Suppress("SameParameterValue")
    private fun testFetchArtist(artistId: Long) {
        lifecycleScope.launch {
            discogsRepositoryV2.fetchArtist(artistId).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchArtist encountered an error" }
                    },
                    fb = {
                        Loginator.d { "artist = $it" }
                    },
                    fab = { th: Throwable, artist: Artist? ->
                        Loginator.e(throwable = th) { "fetchArtist encountered an error" }
                        Loginator.d { "artist = $artist" }
                    }
                )
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchLabel(labelId: Long) {
        lifecycleScope.launch {
            discogsRepositoryV2.fetchLabel(labelId).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchLabel encountered an error" }
                    },
                    fb = {
                        Loginator.d { "label = $it" }
                    },
                    fab = { th: Throwable, label: Label? ->
                        Loginator.e(throwable = th) { "fetchLabel encountered an error" }
                        Loginator.d { "label = $label" }
                    }
                )
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchMaster(masterId: Long) {
        lifecycleScope.launch {
            discogsRepositoryV2.fetchMaster(masterId).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchMaster encountered an error" }
                    },
                    fb = {
                        Loginator.d { "master = $it" }
                    },
                    fab = { th: Throwable, master: Master? ->
                        Loginator.e(throwable = th) { "fetchMaster encountered an error" }
                        Loginator.d { "master = $master" }
                    }
                )
            }
        }
    }

    /*
    @Suppress("Unused")
    private fun testFetchRelease(releaseId: Long) {
        lifecycleScope.launch {
            discogsWebserviceV2.getRelease(releaseId)
                .onLeft { error ->
                    val x = "$error"
                }
                .onRight { remoteRelease ->
                    val x = "$remoteRelease"
                }
        }
    }
     */

    @Suppress("Unused")
    companion object {
        const val TAYLOR_SWIFT = 1124645L
        const val REPUBLIC_RECORDS = 38017L
        const val ATLANTIC_RECORDS = 681L
        const val THE_TORTURED_POETS_DEPARTMENT_MASTER = 3461018L // Master
        const val AN_HOUR_BEFORE_ITS_DARK_MASTER = 2531977L // Master
        const val THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY = 30438707L // Release
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
            onClick = {
                onFetchData()
            },
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
