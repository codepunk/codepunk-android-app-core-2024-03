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
import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var discogsWebservice: DiscogsWebservice

    @Inject
    lateinit var discogsRepository: DiscogsRepository

    @Inject
    lateinit var allDao: AllDao

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
                        name = "Codepunk",
                        onFetchData = ::testFetchStuff,
                        onDeleteData = ::testDeleteStuff
                    )
                }
            }
        }
    }

    private fun testFetchStuff() {
        //testFetchArtist(TAYLOR_SWIFT)
        //testFetchLabel(REPUBLIC_RECORDS)
        //testFetchLabel(ATLANTIC_RECORDS)
        testFetchMaster(AN_HOUR_BEFORE_ITS_DARK_MASTER)
        testFetchMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
        //testFetchMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
        //testFetchRelease(AN_HOUR_BEFORE_ITS_DARK_RELEASE)
        //testFetchRelease(THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY)
    }

    private fun testDeleteStuff() {
        lifecycleScope.launch {
            allDao.deleteResourceAndMaster(AN_HOUR_BEFORE_ITS_DARK_MASTER)
            allDao.deleteResourceAndMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
        }
    }

    @Suppress("SameParameterValue")
    private fun testFetchArtist(artistId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchArtist(artistId).collect { result ->
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
            discogsRepository.fetchLabel(labelId).collect { result ->
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
            discogsRepository.fetchMaster(masterId).collect { result ->
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

    @Suppress("Unused")
    private fun testFetchRelease(releaseId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchRelease(releaseId).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchRelease encountered an error" }
                    },
                    fb = {
                        Loginator.d { "release = $it" }
                    },
                    fab = { th: Throwable, release: Release? ->
                        Loginator.e(throwable = th) { "fetchRelease encountered an error" }
                        Loginator.d { "release = $release" }
                    }
                )
            }
        }
    }

    @Suppress("Unused")
    companion object {
        const val TAYLOR_SWIFT = 1124645L
        const val REPUBLIC_RECORDS = 38017L
        const val ATLANTIC_RECORDS = 681L
        const val THE_TORTURED_POETS_DEPARTMENT_MASTER = 3461018L // Master
        const val AN_HOUR_BEFORE_ITS_DARK_MASTER = 2531977L // Master
        const val THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY = 30438707L // Release
        const val AN_HOUR_BEFORE_ITS_DARK_RELEASE = 22351390L // Master
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onFetchData: () -> Unit = {},
    onDeleteData: () -> Unit = {}
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
        Button(
            onClick = {
                onDeleteData()
            },
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text("Delete Data")
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
