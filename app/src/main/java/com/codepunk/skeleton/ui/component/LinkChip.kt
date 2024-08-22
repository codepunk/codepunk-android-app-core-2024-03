package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.util.url.Domain
import com.codepunk.skeleton.util.url.UrlInfo

@Composable
fun LinkChip(
    urlInfo: UrlInfo,
    count: Int = 0
) {
    val uriHandler = LocalUriHandler.current
    val urlName = stringResource(id = urlInfo.domain.nameRef)
    val label = buildString {
        val display = when (val domain = urlInfo.domain) {
            Domain.OTHER -> urlInfo.display
            else -> stringResource(id = domain.nameRef)
        }
        append(display)
        if (urlInfo.domain != Domain.OTHER && count > 1 && urlInfo.qualifier.isNotEmpty()) {
            append(" (")
            append(urlInfo.qualifier)
            append(")")
        }
    }
    AssistChip(
        onClick = { uriHandler.openUri(urlInfo.urlString) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = urlInfo.domain.iconRef),
                contentDescription = urlName,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        },
        label = { Text(text = label) }
    )
}
