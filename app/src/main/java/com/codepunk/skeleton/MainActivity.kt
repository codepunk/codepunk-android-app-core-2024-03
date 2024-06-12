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
import com.codepunk.skeleton.data.local.dao.ReleaseDao
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.LocalReleaseFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var discogsWebService: DiscogsWebService

    @Inject
    lateinit var releaseDao: ReleaseDao

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
                        name = "Android",
                        onClick = ::onSearch
                    )
                }
            }
        }
    }

    private fun onSearch() {
        lifecycleScope.launch {
            /*
            val responseMillis = measureTimeMillis {
                val response = discogsWebService.search("marillion")
                val x = "$response"
            }
            Loginator.d { "responseMillis = $responseMillis" }

             */
            insertData()
        }
    }

    private suspend fun insertData() {
        val releaseWithDetails = LocalReleaseWithDetails(
            release = LocalRelease(
                id = 1736094,
                title = "Marillion - Marillion.com",
                masterId = 16415,
                masterUrl = "https://api.discogs.com/masters/16415",
                uri = "/release/1736094-Marillion-Marillioncom",
                thumb = "https://i.discogs.com/kw1trpn6c7w-cK3vExBDYjhTYsdsClZT8s-H-afV4Rc/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE3MzYw/OTQtMTMwNTMxMjUx/OC5qcGVn.jpeg",
                coverImage = "https://i.discogs.com/MTp8qicmBlFkvvlcueaDoWd8wDBR1-r4zfxq3Zi78eU/rs:fit/g:sm/q:90/h:596/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE3MzYw/OTQtMTMwNTMxMjUx/OC5qcGVn.jpeg",
                resourceUrl = "https://api.discogs.com/releases/1736094",
                country = "US",
                year = "1999",
                categoryNumber = "NR4505",
                formatQuantity = 1
            ),
            format = listOf("CD", "Promo", "Album"),
            label = listOf(
                "Sanctuary",
                "Marillion",
                "Sanctuary Records",
                "Marillion",
                "The Racket Club",
                "Chop 'Em Out",
                "No Man's Land",
                "The Forge",
                "The Racket Club"
            ),
            genre = listOf("Rock"),
            style = listOf("Prog Rock"),
            barcode = listOf(
                "5 0 6 3 9 414424",
                "03701 MARILLION SP F 17007 100499",
                "IFPI LD81",
                "IFPI 8Y04"
            ),
            formats = listOf(
                LocalReleaseFormatWithDescriptions(
                    releaseFormat = LocalReleaseFormat(
                        releaseId = 1736094,
                        name = "CD",
                        quantity = 1
                    ),
                    descriptions = listOf(
                        LocalReleaseFormatDescription(description = "Compilation")
                    )
                )
            )
        )

        releaseDao.upsertReleaseWithDetails(releaseWithDetails)

        val x = "$releaseWithDetails"

        /*
        val upsertFormatMillis = measureTimeMillis {
            formatsWithDescriptions.forEach {
                releaseDao.upsertReleaseFormatWithDescriptions(it)
            }
        }
        Loginator.d { "upsertFormatMillis = $upsertFormatMillis" }

        val releaseDetails = listOf(
            ReleaseDetail(type = FORMAT, index = 0, detail = "Vinyl"),
            ReleaseDetail(type = FORMAT, index = 1, detail = "LP"),
            ReleaseDetail(type = FORMAT, index = 2, detail = "Album"),
            ReleaseDetail(type = LABEL, index = 0, detail = "Madfish"),
            ReleaseDetail(type = LABEL, index = 1, detail = "Intact Recordings"),
            ReleaseDetail(type = GENRE, index = 0, detail = "Rock")
        )
        releaseDao.upsertReleaseDetails(releaseDetails)

        val releaseFormat2: List<ReleaseFormatWithDescriptions>
        val releaseFormat4: List<ReleaseFormatWithDescriptions>
        val queryMillis = measureTimeMillis {
            releaseFormat2 = releaseDao.getReleaseFormatWithDescriptions(formatId = 2)
            releaseFormat4 = releaseDao.getReleaseFormatWithDescriptions(formatId = 4)
        }
        val x = "$releaseFormat2 $releaseFormat4"
        Loginator.d { "queryMillis = $queryMillis" }
        */

    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
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
            onClick = { onClick() },
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text("Search")
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
