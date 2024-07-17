package com.codepunk.skeleton.ui.composeables.collapsingtoolbar

import androidx.compose.runtime.saveable.mapSaver

class ScrollState(
    heightRange: IntRange,
    scrollValue: Int = 0
) : ScrollFlagState(
    heightRange = heightRange,
    scrollValue = scrollValue
) {

    override var scrollValue: Int
        get() = _scrollValue
        set(value) {
            _scrollValue = value.coerceAtLeast(0)
        }

    override val height: Float
        get() = (maxHeight.toFloat() - scrollValue).coerceIn(minHeight.toFloat(), maxHeight.toFloat())

    override val offset: Float
        get() = -(scrollValue - rangeDifference.toFloat()).coerceIn(0f, minHeight.toFloat())

    companion object {
        val Saver = run {

            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollValueKey = "ScrollValue"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollValueKey to it.scrollValue
                    )
                },
                restore = {
                    ScrollState(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollValue = it[scrollValueKey] as Int
                    )
                }
            )
        }
    }
}