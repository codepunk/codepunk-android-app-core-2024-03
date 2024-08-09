package com.codepunk.skeleton.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import com.codepunk.skeleton.R

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    profileHtml: String
) {
    if (profileHtml.isNotEmpty()) {
        val colorScheme = MaterialTheme.colorScheme
        val collapsedLines = integerResource(id = R.integer.profile_collapsed_lines)
        var expandable by remember { mutableStateOf(false) }
        var expanded by remember { mutableStateOf(false) }
        var textWidth by remember { mutableIntStateOf(0) }
        val textMeasurer = rememberTextMeasurer()

        val annotatedProfile = remember(profileHtml) {
            AnnotatedString.Companion.fromHtml(
                htmlString = profileHtml,
                linkStyles = TextLinkStyles(
                    style = SpanStyle(
                        color = colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                ),
                linkInteractionListener = null
            )
        }

        Text(
            modifier = Modifier
                .animateContentSize()
                .onGloballyPositioned { text ->
                    textWidth = text.size.width
                    val expandedLines = textMeasurer.measure(
                        text = annotatedProfile,
                        constraints = Constraints(maxWidth = textWidth)
                    ).lineCount
                    expandable = (expandedLines > collapsedLines)
                },
            text = annotatedProfile,
            maxLines = if (expanded) Int.MAX_VALUE else collapsedLines
        )
        if (expandable) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(
                        text = if (expanded) {
                            stringResource(id = R.string.show_less)
                        } else {
                            stringResource(id = R.string.show_more)
                        }
                    )
                }
            }
        }
    }
}
