package com.codepunk.skeleton.domain

import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Label

fun Artist?.orEmpty(): Artist = this ?: Artist()

fun Label?.orEmpty(): Label = this ?: Label()
