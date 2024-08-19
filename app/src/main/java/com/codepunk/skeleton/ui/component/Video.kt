package com.codepunk.skeleton.ui.component

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Video
import com.codepunk.skeleton.domain.toThumbnailUrl
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.util.parseElapsedTimeString
import kotlin.time.Duration

@Composable
fun Video(
    modifier: Modifier = Modifier,
    video: Video
) {
    val placeholder: Painter? = if (LocalInspectionMode.current) {
        video.uri.toIntOrNull()?.let { painterResource(id = it) }
    } else null

    val videoThumbnail = video.uri.toIntOrNull()?.toString() ?: video.toThumbnailUrl()
    //val placeholder = previewPainter(value = videoThumbnail)

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier.size(128.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(videoThumbnail ?: "")
                .build(),
            placeholder = placeholder,
            contentDescription = stringResource(
                R.string.image_of,
                video.title
            ),
            contentScale = ContentScale.Inside
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = video.title,
                maxLines = 2
            )
            if (video.duration > 0) {
                val duration = DateUtils.formatElapsedTime(video.duration.toLong())
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            Text(
                text = video.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}

@Preview
@Composable
fun VideoPreviewDark() {
    val video = Video(
        title = "Taylor Swift - Fortnight (feat. Post Malone) (Official Music Video)",
        description = "The official music video for “Fortnight (feat. Post Malone)” by Taylor Swift, from ‘THE TORTURED POETS DEPARTMENT’.\n\nBuy/download/stream ‘THE TORTURED POETS DEPARTMENT’: https://taylor.lnk.to/thetorturedpoetsdepartment \n\nJoin the #ForAFortnightChallenge o",
        uri = R.drawable.img_preview_2_daniela_kokina_unsplash.toString(),
        duration = 250,
        embed = true

    )
    SkeletonTheme(darkTheme = true) {
        Surface {
            Video(
                video = video
            )
        }
    }
}