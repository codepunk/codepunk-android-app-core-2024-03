package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.codepunk.skeleton.util.url.UrlInfo

@Composable
fun LinkChip(
    urlInfo: UrlInfo,
    count: Int
) {
    val uriHandler = LocalUriHandler.current
    val urlName = stringResource(id = urlInfo.domain.nameRef)
    val label = buildString {
        append(urlInfo.getDomainString(LocalContext.current))
        if (count > 1 && urlInfo.lastPathSegment.isNotEmpty()) {
            append(" (")
            append(urlInfo.lastPathSegment)
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
