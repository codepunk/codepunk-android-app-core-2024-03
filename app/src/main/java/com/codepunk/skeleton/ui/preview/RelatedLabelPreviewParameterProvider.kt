package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.domain.model.RelatedLabel

class RelatedLabelPreviewParameterProvider : PreviewParameterProvider<RelatedLabel> {
    override val values: Sequence<RelatedLabel> = sequenceOf(
        RelatedLabel(
            name = "Record Label One",
        ),
        RelatedLabel(
            name = "Record Label Two",
        ),
        RelatedLabel(
            name = "Record Label Three",
        ),
        RelatedLabel(
            name = "Record Label Four",
        ),
        RelatedLabel(
            name = "Record Label Five",
        ),
        RelatedLabel(
            name = "Record Label Six",
        ),
        RelatedLabel(
            name = "Record Label Seven",
        ),
        RelatedLabel(
            name = "Record Label Eight",
        )
    )
}