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

    /*
    private fun insertData() {
        lifecycleScope.launch {
            val artist: LocalArtistWithDetails = LocalArtistWithDetails(
                artist = LocalArtist(
                    name = "Marillion",
                    id = 218108,
                    resourceUrl = "https://api.discogs.com/artists/218108",
                    uri = "https://www.discogs.com/artist/218108-Marillion",
                    releasesUrl = "https://api.discogs.com/artists/218108/releases",
                    profile = "Marillion formed in Aylesbury, Buckinghamshire, England in 1979. Recording consistently since 1982, their output is generally regarded as comprising two distinct eras, that of original vocalist [a=Fish] who helmed the band at arguably their most popular (critically and commercially), and his replacement, former [a=Europeans] member [a=Steve Hogarth], who joined the band after Fish's departure in 1989. The band's differing styles between the two vocalists has often been known to divide fans.",
                    dataQuality = "Needs Vote"
                ),
                images = listOf(
                    LocalImage(
                        type = ImageType.PRIMARY,
                        uri = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        resourceUrl = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        uri150 = "https://i.discogs.com/u1IJF-wOYnaHyUDUZUgYZupKA5yKsLyDAk6vakzIsLE/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        width = 600,
                        height = 450
                    ),
                    LocalImage(
                        type = ImageType.SECONDARY,
                        uri = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        resourceUrl = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        uri150 = "https://i.discogs.com/u1IJF-wOYnaHyUDUZUgYZupKA5yKsLyDAk6vakzIsLE/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        width = 600,
                        height = 450
                    ),
                    LocalImage(
                        type = ImageType.SECONDARY,
                        uri = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        resourceUrl = "https://i.discogs.com/-EQloKsaJ3P5RH7llBKH2pczugJ58jVYYqfPS2BiJKs/rs:fit/g:sm/q:90/h:450/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        uri150 = "https://i.discogs.com/u1IJF-wOYnaHyUDUZUgYZupKA5yKsLyDAk6vakzIsLE/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxODEw/OC0xNjU1MjM1OTUz/LTk1MTcuanBlZw.jpeg",
                        width = 600,
                        height = 450
                    ),
                ),
                details = listOf(
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.URL,
                        detailIdx = 0,
                        detail = "https://www.marillion.com/"
                    ),
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.URL,
                        detailIdx = 1,
                        detail = "https://marillionofficial.bandcamp.com/"
                    ),
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.URL,
                        detailIdx = 2,
                        detail = "https://www.facebook.com/MarillionOfficial/"
                    ),
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.URL,
                        detailIdx = 3,
                        detail = "https://www.instagram.com/marillionofficial/"
                    ),
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.NAME_VARIATION,
                        detailIdx = 0,
                        detail = "Los Trios Marillos"
                    ),
                    LocalArtistDetail(
                        artistId = 218108,
                        detailType = ArtistDetailType.NAME_VARIATION,
                        detailIdx = 1,
                        detail = "マリリオン"
                    )
                ),
                relationships = listOf(
                    LocalArtistRelationship(
                        parentId = 218108,
                        childId = 487676,
                        relationshipType = ArtistRelationshipType.ALIAS,
                        relationshipIdx = 0,
                        name = "Remixomatosis",
                        resourceUrl = "https://api.discogs.com/artists/487676"
                    ),
                    LocalArtistRelationship(
                        parentId = 218108,
                        childId = 421414,
                        relationshipType = ArtistRelationshipType.MEMBER,
                        relationshipIdx = 0,
                        name = "Pete Trewavas",
                        resourceUrl = "https://api.discogs.com/artists/421414",
                        active = true,
                        thumbnailUrl = "https://i.discogs.com/-X-leKHhV3qJla8IbNMnRaG5iPSht1AM_XIH4Pj26Yc/rs:fit/g:sm/q:40/h:240/w:235/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTQyMTQx/NC0xMjkzODg3MTMx/LmpwZWc.jpeg"
                    ),
                    LocalArtistRelationship(
                        parentId = 218108,
                        childId = 421415,
                        relationshipType = ArtistRelationshipType.MEMBER,
                        relationshipIdx = 1,
                        name = "Mark Kelly (4)",
                        resourceUrl = "https://api.discogs.com/artists/421415",
                        active = true,
                        thumbnailUrl = "https://i.discogs.com/6vtEHHFANf-W51x3ee8O0iJT56cloK1xfsVRIU6su4E/rs:fit/g:sm/q:40/h:330/w:235/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTQyMTQx/NS0xMjI0ODUzNzgy/LmpwZWc.jpeg"
                    ),
                    LocalArtistRelationship(
                        parentId = 218108,
                        childId = 1830626,
                        relationshipType = ArtistRelationshipType.GROUP,
                        relationshipIdx = 0,
                        name = "The Anti-Heroin Project",
                        resourceUrl = "https://api.discogs.com/artists/1830626",
                        active = true,
                        thumbnailUrl = "https://i.discogs.com/vh1g1iFcGqeMuaD6kCrbHbbezjrAMpTxmaPo3UTvoPs/rs:fit/g:sm/q:40/h:196/w:257/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTE4MzA2/MjYtMTM4ODM4MzIx/Mi0zNDY2LmpwZWc.jpeg"
                    )
                )
            )


            val id = artistDao.upsertArtistWithDetails(artist)
            val x = "$id"
        }
    }
     */

    private fun fetchData() {
        lifecycleScope.launch {
            discogsRepository.fetchArtist(218108).collect { result ->
                result.fold(
                    fa = {
                        Loginator.e(throwable = it) { "fetchArtist encountered an error" }
                    },
                    fb = {
                        Loginator.d { "artist = $it" }
                    },
                    fab = { throwable: Throwable, artist: Artist? ->
                        Loginator.e(throwable = throwable) { "fetchArtist encountered an error" }
                        Loginator.d { "artist = $artist" }
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
