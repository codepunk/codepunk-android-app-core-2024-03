package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Credit
import com.codepunk.skeleton.ui.preview.CreditPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.util.previewPainter

@Composable
fun Credit(
    thumbnailSize: Dp,
    credit: Credit
) {
    val textMeasurer = rememberTextMeasurer()
    var textWidth by remember { mutableIntStateOf(0) }
    var titleLineCount by remember { mutableIntStateOf(0) }

    Box {
        Column {
            val artistPlaceholderPainter = painterResource(id = R.drawable.placeholder_artist)
            val placeholder = previewPainter(value = credit.thumbnailUrl) {
                artistPlaceholderPainter
            }

            AsyncImage(
                modifier = Modifier
                    .width(thumbnailSize)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(credit.thumbnailUrl)
                    .build(),
                placeholder = placeholder,
                error = artistPlaceholderPainter,
                contentDescription = stringResource(R.string.image_of, credit.name),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(smallPadding))

            Text(
                modifier = Modifier
                    .width(thumbnailSize)
                    .onGloballyPositioned { layoutCoordinates ->
                        textWidth = layoutCoordinates.size.width
                        titleLineCount = textMeasurer.measure(
                            text = credit.name,
                            constraints = Constraints(maxWidth = textWidth)
                        ).lineCount.coerceIn(1, 2)
                    },
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = credit.name,
                overflow = TextOverflow.Ellipsis
            )

            val roleLineCount = 3 - titleLineCount
            Text(
                modifier = Modifier.width(thumbnailSize),
                minLines = roleLineCount,
                maxLines = roleLineCount,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = credit.role,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun CreditPreviewDark(
    @PreviewParameter(
        CreditPreviewParameterProvider::class
    ) credit: Credit
) {
    SkeletonTheme(darkTheme = true) {
        Surface {
            Credit(
                thumbnailSize = 128.dp,
                credit = credit
            )
        }
    }
}
