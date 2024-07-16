package com.codepunk.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.codepunk.skeleton.data.local.dao.AllDao
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.domain.repository.DiscogsRepository
import com.codepunk.skeleton.ui.Navigation
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var discogsWebservice: DiscogsWebservice

    @Inject
    lateinit var discogsRepository: DiscogsRepository

    @Inject
    lateinit var allDao: AllDao

    val artists: MutableMap<Long, Artist> = mutableMapOf()
    val labels: MutableMap<Long, Label> = mutableMapOf()
    val masters: MutableMap<Long, Master> = mutableMapOf()
    val releases: MutableMap<Long, Release> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkeletonTheme {
                Navigation()
                /*
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = MainScreen
                ) {
                    composable<MainScreen> {
                        /*
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Greeting(
                                name = "Codepunk",
                                onFetchData = ::testFetchStuff,
                                onDeleteData = ::testDeleteStuff,
                                onLookAtData = ::lookAtData
                            )
                        }
                         */
                    }
                }
                 */
            }
        }
    }

    /*
    private fun testFetchStuff() {
        testFetchArtist(TAYLOR_SWIFT)
        testFetchArtist(MARILLION)
        testFetchLabel(REPUBLIC_RECORDS)
        testFetchLabel(ATLANTIC_RECORDS)
        testFetchMaster(AN_HOUR_BEFORE_ITS_DARK_MASTER)
        testFetchMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
        //testFetchMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
        testFetchRelease(AN_HOUR_BEFORE_ITS_DARK_RELEASE)
        testFetchRelease(THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY)
        Loginator.d { "All fetch tests complete" }
    }

    private fun testDeleteStuff() {
        lifecycleScope.launch {
            allDao.deleteArtist(TAYLOR_SWIFT)
            allDao.deleteArtist(MARILLION)
            allDao.deleteLabel(REPUBLIC_RECORDS)
            allDao.deleteLabel(ATLANTIC_RECORDS)
            allDao.deleteMaster(AN_HOUR_BEFORE_ITS_DARK_MASTER)
            allDao.deleteMaster(THE_TORTURED_POETS_DEPARTMENT_MASTER)
            allDao.deleteRelease(AN_HOUR_BEFORE_ITS_DARK_RELEASE)
            allDao.deleteRelease(THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY)
            Loginator.d { "All deletes complete" }
        }
    }

    private fun lookAtData() {
        Loginator.d { "Look at data" }
    }

    @Suppress("SameParameterValue")
    private fun testFetchArtist(artistId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchArtist(artistId).collect { result ->
                val artist = result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchArtist encountered an error" }
                        null
                    },
                    fb = {
                        Loginator.d { "artist = $it" }
                        it
                    },
                    fab = { th: Throwable, artist: Artist? ->
                        Loginator.e(throwable = th) { "fetchArtist encountered an error" }
                        Loginator.d { "artist = $artist" }
                        artist
                    }
                )
                artist?.apply { artists[artistId] = this } ?: artists.remove(artistId)
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchLabel(labelId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchLabel(labelId).collect { result ->
                val label = result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchLabel encountered an error" }
                        null
                    },
                    fb = {
                        Loginator.d { "label = $it" }
                        it
                    },
                    fab = { th: Throwable, label: Label? ->
                        Loginator.e(throwable = th) { "fetchLabel encountered an error" }
                        Loginator.d { "label = $label" }
                        label
                    }
                )
                label?.apply { labels[labelId] = this } ?: labels.remove(labelId)
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchMaster(masterId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchMaster(masterId).collect { result ->
                val master = result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchMaster encountered an error" }
                        null
                    },
                    fb = {
                        Loginator.d { "master = $it" }
                        it
                    },
                    fab = { th: Throwable, master: Master? ->
                        Loginator.e(throwable = th) { "fetchMaster encountered an error" }
                        Loginator.d { "master = $master" }
                        master
                    }
                )
                master?.apply { masters[masterId] = this } ?: masters.remove(masterId)
            }
        }
    }

    @Suppress("Unused")
    private fun testFetchRelease(releaseId: Long) {
        lifecycleScope.launch {
            discogsRepository.fetchRelease(releaseId).collect { result ->
                val release = result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchRelease encountered an error" }
                        null
                    },
                    fb = {
                        Loginator.d { "release = $it" }
                        it
                    },
                    fab = { th: Throwable, release: Release? ->
                        Loginator.e(throwable = th) { "fetchRelease encountered an error" }
                        Loginator.d { "release = $release" }
                        release
                    }
                )
                release?.apply { releases[releaseId] = this } ?: releases.remove(releaseId)
            }
        }
    }
     */

    /*
    @Suppress("Unused", "SpellCheckingInspection")
    companion object {
        const val TAYLOR_SWIFT = 1124645L
        const val MARILLION = 218108L
        const val REPUBLIC_RECORDS = 38017L
        const val ATLANTIC_RECORDS = 681L
        const val THE_TORTURED_POETS_DEPARTMENT_MASTER = 3461018L // Master
        const val AN_HOUR_BEFORE_ITS_DARK_MASTER = 2531977L // Master
        const val THE_TORTURED_POSTS_DEPARTMENT_THE_ANTHOLOGY = 30438707L // Release
        const val AN_HOUR_BEFORE_ITS_DARK_RELEASE = 22351390L // Master
    }
     */
}

