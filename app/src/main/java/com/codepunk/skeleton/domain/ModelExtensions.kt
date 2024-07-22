package com.codepunk.skeleton.domain

import com.codepunk.skeleton.domain.model.Artist

fun Artist?.orEmpty(): Artist = this ?: Artist()