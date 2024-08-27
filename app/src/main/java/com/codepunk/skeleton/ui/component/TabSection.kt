package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import kotlinx.coroutines.launch

@Composable
fun <T> TabSection(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    textOf: (T) -> String,
    content: @Composable (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    var selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    /*
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex.value)
    }
     */

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex.value
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = textOf(tab))
                    }
                )
            }
        }

        HorizontalPager(
            //modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = true
        ) { index ->
            content(index)
        }
    }
}
