package com.codepunk.skeleton.ui.screen.label

sealed class LabelScreenEvent {

    data class LoadLabel(val labelId: Long) : LabelScreenEvent()

    data object RefreshLabel : LabelScreenEvent()

}
