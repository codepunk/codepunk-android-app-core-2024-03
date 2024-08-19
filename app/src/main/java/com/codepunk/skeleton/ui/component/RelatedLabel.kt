package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedLabel
import com.codepunk.skeleton.ui.preview.RelatedLabelPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding

@Composable
fun RelatedLabel(
    modifier: Modifier = Modifier,
    iconSize: Dp,
    relatedLabel: RelatedLabel
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .width(iconSize)
                .aspectRatio(1f)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.img_lp_500),
            contentDescription = stringResource(R.string.label),
        )

        Spacer(modifier = Modifier.height(smallPadding))

        Text(
            modifier = Modifier.width(iconSize),
            minLines = 1,
            maxLines = 3,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            text = relatedLabel.name,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showSystemUi = false)
@Composable
fun RelatedLabelPreviewDark(
    @PreviewParameter(
        RelatedLabelPreviewParameterProvider::class
    ) relatedLabel: RelatedLabel
) {
    SkeletonTheme(darkTheme = true) {
        Surface(
            modifier = Modifier
        ) {
            RelatedLabel(
                iconSize = 72.dp,
                relatedLabel = relatedLabel
            )
        }
    }
}
