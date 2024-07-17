package com.codepunk.skeleton.ui.composeables.collapsingtoolbar

abstract class DynamicOffsetScrollFlagState(
    heightRange: IntRange,
    scrollValue: Int
) : ScrollFlagState(heightRange, scrollValue) {

    protected abstract var scrollOffset: Float

}
