package com.codepunk.skeleton.ui.screen.search

sealed class SearchScreenEvent {

    data class Search(val query: String) : SearchScreenEvent()

    data object RefreshSearch : SearchScreenEvent()

}