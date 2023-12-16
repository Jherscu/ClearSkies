package com.jHerscu.clearskies.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.jHerscu.clearskies.ui.theme.CORNER_RADIUS_DP
import com.jHerscu.clearskies.ui.theme.Padding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselComponent(
    count: Int,
    modifier: Modifier = Modifier,
    title: @Composable (ColumnScope.() -> Unit)? = null,
    pagerState: PagerState = rememberPagerState { count },
    showMarkers: Boolean = true,
    pageContent: @Composable (PagerScope.(Int) -> Unit)
) {
    val endPadding = LocalConfiguration.current.screenWidthDp.dp * .5f
    Column(
        modifier = modifier
            .border(
                width = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(CORNER_RADIUS_DP.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        title?.let {
            ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
                it.invoke(this)
            }
            Spacer(modifier = Modifier.height(Padding.SMALL.dp))
        }
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(
                start = Padding.LARGE.dp,
                end = endPadding,
                top = Padding.SMALL.dp,
                bottom = Padding.SMALL.dp,
            ),
            pageSpacing = Padding.STANDARD.dp
        ) {
            pageContent(it)
        }
        if (showMarkers) {
            CarouselItemMarkers(
                modifier = Modifier
                    .padding(Padding.SMALL.dp)
                    .fillMaxWidth(),
                count = count,
                pagerState = pagerState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselItemMarkers(
    count: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clearAndSetSemantics {}, // Unregister from TalkBack traversal
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(count) {
            val color = with(MaterialTheme.colorScheme) {
                if (it == pagerState.currentPage) outline else outlineVariant
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = Padding.EXTRA_SMALL.dp)
                    .size(Padding.SMALL.dp)
                    .drawBehind { this.drawCircle(color) }
            )
        }
    }
}
