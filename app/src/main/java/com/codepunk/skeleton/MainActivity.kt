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
import com.codepunk.skeleton.data.local.entity.ReleaseFormat
import com.codepunk.skeleton.data.local.entity.ReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.ReleaseFormatWithDescriptions
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
            val response = discogsWebService.search("marillion")
            val x = "$response"
            insertData()
        }
    }

    private suspend fun insertData() {
        val formatsWithDescriptions = listOf(
            ReleaseFormatWithDescriptions(
                releaseFormat = ReleaseFormat(
                    name = "CD",
                    quantity = 1,
                    text = "Slipcase"
                ),
                descriptions = listOf(
                    ReleaseFormatDescription(
                        description = "Album"
                    )
                )
            ),
            ReleaseFormatWithDescriptions(
                releaseFormat = ReleaseFormat(
                    name = "Vinyl",
                    quantity = 2
                ),
                descriptions = listOf(
                    ReleaseFormatDescription(
                        description = "LP"
                    ),
                    ReleaseFormatDescription(
                        description = "Album"
                    ),
                    ReleaseFormatDescription(
                        description = "Reissue"
                    )
                )
            ),
            ReleaseFormatWithDescriptions(
                releaseFormat = ReleaseFormat(
                    name = "CD",
                    quantity = 1,
                ),
                descriptions = listOf(
                    ReleaseFormatDescription(
                        description = "Album"
                    )
                )
            ),
            ReleaseFormatWithDescriptions(
                releaseFormat = ReleaseFormat(
                    name = "Vinyl",
                    quantity = 4,
                    text = "180 Gram"
                ),
                descriptions = listOf(
                    ReleaseFormatDescription(
                        description = "LP"
                    ),
                    ReleaseFormatDescription(
                        description = "Album"
                    ),
                    ReleaseFormatDescription(
                        description = "Compilation"
                    )
                )
            ),
            ReleaseFormatWithDescriptions(
                releaseFormat = ReleaseFormat(
                    name = "Box Set",
                    quantity = 1
                )
            )
        )

        releaseDao.upsertReleaseFormatsWithDescriptions(formatsWithDescriptions)

        val releaseFormat = releaseDao.getReleaseFormatWithDescriptions(5L)
        val x = "$releaseFormat"
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
