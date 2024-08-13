package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedLabel
import com.codepunk.skeleton.ui.preview.RelatedLabelPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.smallPadding
import com.codepunk.skeleton.ui.theme.touchSize

@Composable
fun RelatedLabel(
    modifier: Modifier = Modifier,
    relatedLabel: RelatedLabel
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(smallPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_lp_500),
            contentDescription = stringResource(id = R.string.label)
        )
        Spacer(modifier = Modifier.width(smallPadding))
        Text(
            modifier = modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = relatedLabel.name,
            textAlign = TextAlign.Start
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
                .fillMaxWidth()
                .height(touchSize)
        ) {
            RelatedLabel(
                relatedLabel = relatedLabel
            )
        }
    }
}
