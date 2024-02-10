package com.jHerscu.clearskies.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 2 **/
const val BASE_TONAL_ELEVATION_DP = 2

/** 3 **/
const val CARD_TONAL_ELEVATION_DP = 3

/** 4 **/
const val ON_CARD_TONAL_ELEVATION_DP = 4

const val CORNER_RADIUS_DP = 16

// Based on Material 3 Spacing Recommendations
private const val DIMEN_EXTRA_LARGE = 24
private const val DIMEN_LARGE = 20
private const val DIMEN_STANDARD = 16
private const val DIMEN_MEDIUM = 12
private const val DIMEN_SMALL = 8
private const val DIMEN_EXTRA_SMALL = 4

enum class Dimen(val dp: Dp) {
    /** 4 **/
    EXTRA_SMALL(DIMEN_EXTRA_SMALL.dp),

    /** 8 **/
    SMALL(DIMEN_SMALL.dp),

    /** 12 **/
    MEDIUM(DIMEN_MEDIUM.dp),

    /** 16 **/
    STANDARD(DIMEN_STANDARD.dp),

    /** 20 **/
    LARGE(DIMEN_LARGE.dp),

    /** 24 **/
    EXTRA_LARGE(DIMEN_EXTRA_LARGE.dp),
}
