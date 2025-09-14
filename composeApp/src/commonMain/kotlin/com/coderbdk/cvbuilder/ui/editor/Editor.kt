package com.coderbdk.cvbuilder.ui.editor

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderbdk.cvbuilder.data.model.CVDivider
import com.coderbdk.cvbuilder.data.model.CVImage
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVList
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.Component
import com.coderbdk.cvbuilder.data.model.PropertyKeys
import com.coderbdk.cvbuilder.ui.editor.components.EditorImage
import com.coderbdk.cvbuilder.util.CVColor
import com.coderbdk.cvbuilder.util.getPropertyValue
import com.coderbdk.cvbuilder.util.toColor
import kotlin.math.abs

@Composable
fun RenderEditorPage(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    isEnabledShowParent: Boolean,
    colScope: ColumnScope
) {

    uiState.currentPage.components.forEachIndexed { index, comp ->
        RenderEditorComponent(
            index,
            "$index",
            comp,
            uiState.selectedComponentDepthIndex,
            null,
            colScope,
            isEnabledShowParent,
        ) { depthIndex, depthComponent ->
            onEvent(EditorUiEvent.SelectComponent(depthIndex, depthComponent))
        }
    }
}


@Composable
private fun RenderEditorComponent(
    depthIndex: Int,
    componentDepth: String,
    component: Component,
    selectedComponentDepthIndex: String?,
    rowScope: RowScope?,
    colScope: ColumnScope?,
    isEnabledShowParent: Boolean,
    onSelect: (String, Component) -> Unit,
) {
    val isSelected = componentDepth == selectedComponentDepthIndex

    val width = component.layoutProperties.width
    val height = component.layoutProperties.height
    val weight = component.layoutProperties.weight
    val paddingStart = abs(component.layoutProperties.paddingStart.toUInt().toInt())
    val paddingTop = abs(component.layoutProperties.paddingTop.toUInt().toInt())
    val paddingEnd = abs(component.layoutProperties.paddingEnd.toUInt().toInt())
    val paddingBottom = abs(component.layoutProperties.paddingBottom.toUInt().toInt())

    var modifier: Modifier = if (width == 0 && height == 0) Modifier
    else if (width == 1 && height == 0) Modifier.fillMaxWidth()
    else if (width == 0 && height == 1) Modifier.fillMaxHeight()
    else if (width == 1 && height == 1) Modifier.fillMaxSize()
    else if (width < 0 && height == 0) Modifier.fillMaxWidth(abs(width) / 100f).wrapContentHeight()
    else if (width == 0 && height < 0) Modifier.fillMaxHeight(abs(width) / 100f).wrapContentWidth()
    else if (width > 0 && height == 0) Modifier.width(width.dp)
    else if (width == 0 && height > 0) Modifier.height(height.dp)
    else Modifier.size(width.dp, height.dp)

    if (weight != -1f) {
        rowScope?.apply {
            modifier = modifier.weight(weight)
        }
        colScope?.apply {
            modifier = Modifier.weight(weight)
        }

    }

    val isParentEmpty = (component is CVLayout) && component.children.isEmpty()
    val showParent =
        (isEnabledShowParent && (component is CVLayout || component is CVDivider)) or isParentEmpty

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    modifier = modifier
        .padding(if (showParent) 4.dp else 0.dp)
        .border(
            width = 1.dp,
            if (isSelected) Color.Blue else if (showParent) Color.LightGray else Color.Transparent
        )
        .border(width = 1.dp, color = if (isHovered) Color.Cyan else Color.Transparent)
        .hoverable(interactionSource = interactionSource)
        .clickable(interactionSource = null, indication = null) {
            onSelect(
                componentDepth,
                component
            )
        }
        .padding(if (showParent) 8.dp else 0.dp)
        .padding(
            start = paddingStart.dp,
            top = paddingTop.dp,
            end = paddingEnd.dp,
            bottom = paddingBottom.dp
        ).background(
            color = CVColor.parse(component.layoutProperties.background).toColor()
        )

    when (component) {
        is CVLayout -> {
            val orientation = component.orientation

            if (orientation == CVLayout.Orientation.Vertical) {
                val arrangement = when (component.arrangement) {
                    CVLayout.Arrangement.Top -> Arrangement.Top
                    CVLayout.Arrangement.Bottom -> Arrangement.Bottom
                    CVLayout.Arrangement.Center -> Arrangement.Center
                    CVLayout.Arrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    CVLayout.Arrangement.SpaceBetween -> Arrangement.SpaceBetween
                    CVLayout.Arrangement.SpaceAround -> Arrangement.SpaceAround
                    else -> Arrangement.Top
                }
                val alignment = when (component.alignment) {
                    CVLayout.Alignment.Start -> Alignment.Start
                    CVLayout.Alignment.End -> Alignment.End
                    CVLayout.Alignment.CenterHorizontally -> Alignment.CenterHorizontally
                    else -> Alignment.Start
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = arrangement,
                    horizontalAlignment = alignment
                ) {
                    component.children.forEachIndexed { index, child ->
                        RenderEditorComponent(
                            depthIndex = depthIndex + 1,
                            componentDepth = "$componentDepth $index",
                            component = child,
                            selectedComponentDepthIndex = selectedComponentDepthIndex,
                            rowScope = null,
                            colScope = this,
                            isEnabledShowParent = isEnabledShowParent,
                            onSelect = onSelect
                        )
                    }
                }
            } else if (orientation == CVLayout.Orientation.Horizontal) {
                val arrangement = when (component.arrangement) {
                    CVLayout.Arrangement.Start -> Arrangement.Start
                    CVLayout.Arrangement.End -> Arrangement.End
                    CVLayout.Arrangement.Center -> Arrangement.Center
                    CVLayout.Arrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    CVLayout.Arrangement.SpaceBetween -> Arrangement.SpaceBetween
                    CVLayout.Arrangement.SpaceAround -> Arrangement.SpaceAround
                    else -> Arrangement.Start
                }

                val alignment = when (component.alignment) {
                    CVLayout.Alignment.Top -> Alignment.Top
                    CVLayout.Alignment.CenterVertically -> Alignment.CenterVertically
                    CVLayout.Alignment.Bottom -> Alignment.Bottom
                    else -> Alignment.Top
                }
                Row(
                    modifier = modifier,
                    horizontalArrangement = arrangement,
                    verticalAlignment = alignment
                ) {
                    component.children.forEachIndexed { index, child ->
                        RenderEditorComponent(
                            depthIndex = depthIndex + 1,
                            componentDepth = "$componentDepth $index",
                            component = child,
                            selectedComponentDepthIndex = selectedComponentDepthIndex,
                            rowScope = this,
                            colScope = null,
                            isEnabledShowParent = isEnabledShowParent,
                            onSelect = onSelect
                        )
                    }
                }
            }
        }

        is CVText -> {
            CVTextComponent(component, modifier)
        }

        is CVDivider -> {
            CVDividerComponent(component, modifier)
        }

        is CVList -> {
            CVListComponent(component, modifier)
        }

        is CVImage -> {
            CVImageComponent(component, modifier)
        }
    }

}

@Composable
private fun CVTextComponent(component: CVText, modifier: Modifier) {
    Text(
        text = component.text,
        color = CVColor.parse(component.textColor).toColor(),
        fontSize = component.fontSize.sp,
        fontFamily = component.fontFamily.fontFamily,
        textAlign = component.textAlign.textAlign,
        fontWeight = if (component.bold) FontWeight.Bold else null,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
private fun CVDividerComponent(component: CVDivider, modifier: Modifier) {
    if (component.orientation == CVDivider.Orientation.Horizontal
    ) {
        HorizontalDivider(
            modifier = modifier,
            thickness = component.thickness.dp,
            color = CVColor.parse(component.color).toColor()
        )
    } else {
        VerticalDivider(
            modifier = modifier,
            thickness = component.thickness.dp,
            color = CVColor.parse(component.color).toColor()
        )
    }
}

@Composable
private fun CVListComponent(component: CVList, modifier: Modifier) {
    Column(modifier = modifier) {
        component.items.forEachIndexed { index, text ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = when (component.style) {
                        CVList.Style.Bullet -> "• "
                        CVList.Style.Dashed -> "- "
                        CVList.Style.Numbered -> "${index + 1}. "
                    },
                    color = CVColor.parse(component.textColor).toColor(),
                    fontSize = component.fontSize.sp,
                    textAlign = component.textAlign.textAlign,
                    fontWeight = if (component.bold) FontWeight.Bold else null,
                )

                ListText(component, text)
            }
        }
    }

}

@Composable
private fun ListText(component: CVList, text: String) {
    val annotatedString =  buildAnnotatedString {
        val parts = text.split(Regex("\\*\\*(.*?)\\*\\*"))
        var startIndex = 0
        parts.forEachIndexed { index, part ->
            append(part)
            startIndex += part.length
            if (index < parts.size - 1) {
                val boldText =
                    text.substring(startIndex + 2, text.indexOf("**", startIndex + 2))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(boldText)
                }
                startIndex += boldText.length + 4
            }
        }
    }
    Text(
        text = annotatedString,
        color = CVColor.parse(component.textColor).toColor(),
        fontSize = component.fontSize.sp,
        fontFamily = component.fontFamily.fontFamily,
        textAlign = component.textAlign.textAlign,
        fontWeight = if (component.bold) FontWeight.Bold else null,
    )
}

@Composable
private fun CVImageComponent(component: CVImage, modifier: Modifier) {
    EditorImage(
        data = component.data,
        contentScale = component.scale.contentScale,
        modifier = modifier
    )
}


@Composable
fun RenderPreviewPage(page: CVPage) {
    Column(
        Modifier.fillMaxSize()
    ) {
        page.components.forEach { component ->
            RenderPreviewComponent(component, null, this)
        }
    }
}

@Composable
fun RenderPreviewPage(page: CVPage, modifier: Modifier = Modifier) {
    Column(
        modifier
    ) {
        page.components.forEach { component ->
            RenderPreviewComponent(component, null, this)
        }
    }
}

@Composable
private fun RenderPreviewComponent(
    component: Component,
    rowScope: RowScope?,
    colScope: ColumnScope?,
) {
    val width = component.layoutProperties.width
    val height = component.layoutProperties.height
    val weight = component.layoutProperties.weight
    val paddingStart = abs(component.layoutProperties.paddingStart.toUInt().toInt())
    val paddingTop = abs(component.layoutProperties.paddingTop.toUInt().toInt())
    val paddingEnd = abs(component.layoutProperties.paddingEnd.toUInt().toInt())
    val paddingBottom = abs(component.layoutProperties.paddingBottom.toUInt().toInt())

    var modifier: Modifier = if (width == 0 && height == 0) Modifier
    else if (width == 1 && height == 0) Modifier.fillMaxWidth()
    else if (width == 0 && height == 1) Modifier.fillMaxHeight()
    else if (width == 1 && height == 1) Modifier.fillMaxSize()
    else if (width < 0 && height == 0) Modifier.fillMaxWidth(abs(width) / 100f).wrapContentHeight()
    else if (width == 0 && height < 0) Modifier.fillMaxHeight(abs(width) / 100f).wrapContentWidth()
    else if (width > 0 && height == 0) Modifier.width(width.dp)
    else if (width == 0 && height > 0) Modifier.height(height.dp)
    else Modifier.size(width.dp, height.dp)

    if (weight != -1f) {
        rowScope?.apply {
            modifier = modifier.weight(weight)
        }
        colScope?.apply {
            modifier = Modifier.weight(weight)
        }

    }
    modifier = modifier.padding(
        start = paddingStart.dp,
        top = paddingTop.dp,
        end = paddingEnd.dp,
        bottom = paddingBottom.dp
    ).background(
        color = CVColor.parse(component.layoutProperties.background).toColor()
    )

    when (component) {
        is CVLayout -> {
            val orientation = component.orientation
            if (orientation == CVLayout.Orientation.Vertical) {
                val arrangement = when (component.arrangement) {
                    CVLayout.Arrangement.Top -> Arrangement.Top
                    CVLayout.Arrangement.Bottom -> Arrangement.Bottom
                    CVLayout.Arrangement.Center -> Arrangement.Center
                    CVLayout.Arrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    CVLayout.Arrangement.SpaceBetween -> Arrangement.SpaceBetween
                    CVLayout.Arrangement.SpaceAround -> Arrangement.SpaceAround
                    else -> Arrangement.Top
                }
                val alignment = when (component.alignment) {
                    CVLayout.Alignment.Start -> Alignment.Start
                    CVLayout.Alignment.End -> Alignment.End
                    CVLayout.Alignment.CenterVertically -> Alignment.CenterHorizontally
                    else -> Alignment.Start
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = arrangement,
                    horizontalAlignment = alignment
                ) {
                    component.children.forEach { child ->
                        RenderPreviewComponent(child, null, this)
                    }
                }
            } else if (orientation == CVLayout.Orientation.Horizontal) {
                val arrangement = when (component.arrangement) {
                    CVLayout.Arrangement.Start -> Arrangement.Start
                    CVLayout.Arrangement.End -> Arrangement.End
                    CVLayout.Arrangement.Center -> Arrangement.Center
                    CVLayout.Arrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    CVLayout.Arrangement.SpaceBetween -> Arrangement.SpaceBetween
                    CVLayout.Arrangement.SpaceAround -> Arrangement.SpaceAround
                    else -> Arrangement.Start
                }

                val alignment = when (component.alignment) {
                    CVLayout.Alignment.Top -> Alignment.Top
                    CVLayout.Alignment.CenterHorizontally -> Alignment.CenterVertically
                    CVLayout.Alignment.Bottom -> Alignment.Bottom
                    else -> Alignment.Top
                }
                Row(
                    modifier = modifier,
                    horizontalArrangement = arrangement,
                    verticalAlignment = alignment
                ) {
                    component.children.forEach { child ->
                        RenderPreviewComponent(child, this, null)
                    }
                }
            }
        }

        is CVText -> {
            CVTextComponent(component, modifier)
        }

        is CVDivider -> {
            CVDividerComponent(component, modifier)
        }

        is CVList -> {
            CVListComponent(component, modifier)
        }

        is CVImage -> {
            CVImageComponent(component, modifier)
        }
    }
}
