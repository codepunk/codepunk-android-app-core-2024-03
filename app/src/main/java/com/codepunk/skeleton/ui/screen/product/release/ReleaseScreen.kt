package com.codepunk.skeleton.ui.screen.product.release

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.codepunk.skeleton.ui.component.ProductAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseScreen(
    modifier: Modifier = Modifier,
    releaseId: Long,
    state: ReleaseScreenState,
    onEvent: (ReleaseScreenEvent) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onEvent(ReleaseScreenEvent.LoadRelease(releaseId))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val columnScrollState: ScrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProductAppBar(
                scrollBehavior = scrollBehavior,
                product = state.release
            ) {
                Column {
                    Text(
                        text = state.release?.title.orEmpty(),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }
        }
    ) { innerPadding ->
        state.release?.run {

        }
    }
}
