package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun <T> TabSection(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    textOf: (T) -> String
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
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            state = pagerState,
            userScrollEnabled = true
        ) { index ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = textOf(tabs[index]))
            }
        }
    }
}
