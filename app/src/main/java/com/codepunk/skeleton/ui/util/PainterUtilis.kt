package com.codepunk.skeleton.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun previewPainter(
    value: String?,
    fallback: (String?) -> Painter? = { null }
): Painter? = if (LocalInspectionMode.current) {
    value?.toIntOrNull()?.let { resId -> painterResource(id = resId) }
} else {
    fallback(value)
}
