package com.coderbdk.cvbuilder.ui.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BorderAll
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FormatOverline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Window
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.window.core.layout.WindowWidthSizeClass
import com.coderbdk.cvbuilder.data.model.CVDivider
import com.coderbdk.cvbuilder.data.model.CVImage
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVList
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.Component
import com.coderbdk.cvbuilder.getPlatform
import com.coderbdk.cvbuilder.pdf.ExportComposePdf
import com.coderbdk.cvbuilder.ui.editor.components.PropertiesPanel
import com.coderbdk.cvbuilder.util.demoCVJson
import com.coderbdk.cvbuilder.util.toCVTemplate
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    onNavigateUp: () -> Unit
) {
    val navigator = rememberSupportingPaneScaffoldNavigator()
    val scope = rememberCoroutineScope()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val coroutine = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    val pageWidth =  uiState.template.pageWidth.dp
    val pageHeight = uiState.template.pageHeight.dp

    var isCompactWindowSize = false

    val isCompactAndMediumWindowSize = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            isCompactWindowSize = true
            isCompactWindowSize
        }

        WindowWidthSizeClass.MEDIUM -> {
            isCompactWindowSize = false
            true
        }

        WindowWidthSizeClass.EXPANDED -> {
            false
        }

        else -> {
            false
        }
    }

    isCompactWindowSize = isCompactAndMediumWindowSize && isCompactWindowSize


    if (loading) {
        Dialog(
            onDismissRequest = {}
        ) {
            Card {
                LinearProgressIndicator()
            }
        }
    }
    /*    if (uiState.selectedInsertComponent != null) {
            Dialog(
                onDismissRequest = {}
            ) {
                Card {
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        EditorInsertionTool(uiState, onEvent)
                        Button(
                            onClick = {
                                onEvent(EditorUiEvent.ShowInsertDialog(null))
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                }


            }
        }*/

    if (isCompactAndMediumWindowSize && uiState.selectedComponentDepthIndex != null) {
        Dialog(
            onDismissRequest = {}
        ) {
            Card {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            onEvent(EditorUiEvent.CancelSelectComponent)
                        }
                    ) {
                        Icon(Icons.Default.Close, "close")
                    }
                    ComponentSupportingPane(uiState, onEvent) {
                        scope.launch {
                            navigator.navigateTo(SupportingPaneScaffoldRole.Extra)
                        }
                    }

                }
            }


        }

    }
    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {

                    TopAppBar(
                        title = {
                            Text("Editor (${pageWidth.value}x${pageHeight.value})")
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onNavigateUp()
                                }
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                            }
                        },
                        actions = {

                            if (isCompactWindowSize) {
                                IconButton(
                                    onClick = {

                                    }
                                ) {
                                    Icon(Icons.Default.MoreVert, "more option")
                                }
                                return@TopAppBar
                            }
                            OutlinedCard() {
                                Row {
                                    IconButton(
                                        onClick = {
                                            onEvent(EditorUiEvent.DeleteCurrentPage)
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, "delete page")
                                    }
                                    IconButton(
                                        onClick = {
                                            onEvent(EditorUiEvent.CreateNewPage)
                                        }
                                    ) {
                                        Icon(Icons.Default.Add, "create page")
                                    }
                                    if (isCompactAndMediumWindowSize) {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    navigator.navigateTo(SupportingPaneScaffoldRole.Extra)
                                                }
                                            }
                                        ) {
                                            Icon(Icons.Default.Window, "preview")
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            if (!loading) {
                                                coroutine.launch {
                                                    loading = true
                                                    getPlatform().cvFileHandler.import()?.let {
                                                        onEvent(EditorUiEvent.LoadCVTemplate(it))
                                                    }
                                                    loading = false
                                                }
                                            }

                                        }
                                    ) {
                                        Icon(Icons.Default.FileOpen, "Import")
                                    }
                                    IconButton(
                                        onClick = {
                                            if (!loading) {
                                                coroutine.launch {
                                                    loading = true
                                                    getPlatform().cvFileHandler.export(
                                                        template = uiState.template
                                                    )
                                                    loading = false
                                                }
                                            }

                                        }
                                    ) {
                                        Icon(Icons.Default.SaveAs, "Export")
                                    }
                                    IconButton(
                                        onClick = {
                                            onEvent(EditorUiEvent.PdfExporting(true))
                                        }
                                    ) {
                                        Icon(Icons.Default.PictureAsPdf, "Export")
                                    }
                                }
                            }
                        }
                    )
                }
            ) {
                MainEditorPane(
                    uiState,
                    onEvent,
                    pageWidth,
                    pageHeight,
                    modifier = Modifier.padding(it)
                )
            }
        },
        supportingPane = {
            AnimatedPane(modifier = Modifier.safeContentPadding()) {
                ComponentSupportingPane(uiState, onEvent) {
                    scope.launch {
                        navigator.navigateTo(SupportingPaneScaffoldRole.Extra)
                    }
                }
            }

        },
        extraPane = {
            AnimatedPane(modifier = Modifier.safeContentPadding()) {
                ComponentTreePane(uiState, onEvent, isCompactAndMediumWindowSize) {
                    scope.launch {
                        if (navigator.canNavigateBack()) navigator.navigateBack()
                    }
                }
            }
        }
    )
}

@Composable
fun ComponentSupportingPane(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    showPreviewPane: () -> Unit
) {
    val componentTypes = remember {
        listOf(
            CVLayout(children = emptyList()),
            CVText(),
            CVDivider(),
            CVList(),
            CVImage()
        )
    }
    var clickMenuComponentIndex by remember { mutableStateOf(-1) }

    // Components & Properties Panel
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = showPreviewPane
            ) {
                Icon(Icons.Default.Window, "preview")
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                //  EditorTool(uiState, onEvent)
                uiState.selectedComponentDepthIndex?.let {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Components", style = MaterialTheme.typography.titleLarge)
                        IconButton(
                            onClick = {
                                onEvent(EditorUiEvent.Delete(uiState.selectedComponentDepthIndex))
                            }
                        ) {
                            Icon(Icons.Default.Delete, "delete")
                        }

                    }

                    uiState.copyComponent?.let { component ->
                        Box {
                            Row {
                                AssistChip(
                                    onClick = {
                                        clickMenuComponentIndex = -2
                                        onEvent(EditorUiEvent.ShowInsertDialog(component))
                                    },
                                    label = {
                                        Text(component.name)
                                    },
                                    border = BorderStroke(
                                        1.dp,
                                        color = Color.Magenta
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        onEvent(EditorUiEvent.DeleteCopiedComponent)
                                    }
                                ) {
                                    Icon(Icons.Default.Remove, "remove")
                                }
                            }
                            // Insertion Tool
                            if (uiState.selectedInsertComponent != null && clickMenuComponentIndex == -2) {
                                DropdownInsertionMenu(uiState, onEvent, true) {
                                    onEvent(EditorUiEvent.ShowInsertDialog(null))
                                }
                            }
                        }
                        HorizontalDivider()
                    }

                    uiState.selectedComponent?.let { component ->
                        Box {
                            Row {
                                AssistChip(
                                    onClick = {
                                        clickMenuComponentIndex = -3
                                        onEvent(EditorUiEvent.ShowInsertDialog(component))
                                    },
                                    label = {
                                        Text(component.name)
                                    },
                                    border = BorderStroke(
                                        1.dp,
                                        color = Color.Blue
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        onEvent(EditorUiEvent.CopySelectedComponent)
                                    }
                                ) {
                                    Icon(Icons.Default.ContentCopy, "copy")
                                }
                                IconButton(
                                    onClick = {
                                        onEvent(EditorUiEvent.SelectParentComponent(uiState.selectedComponentDepthIndex))
                                    }
                                ) {
                                    Icon(Icons.Outlined.AccountTree, "select parent")
                                }
                            }
                            // Insertion Tool
                            if (uiState.selectedInsertComponent != null && clickMenuComponentIndex == -3) {
                                DropdownInsertionMenu(uiState, onEvent, true) {
                                    onEvent(EditorUiEvent.ShowInsertDialog(null))
                                }
                            }
                        }
                    }
                    FlowRow(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        componentTypes.forEachIndexed { index, component ->
                            Box {
                                AssistChip(
                                    onClick = {
                                        clickMenuComponentIndex = index
                                        onEvent(EditorUiEvent.ShowInsertDialog(component))
                                    },
                                    label = {
                                        Text(component.name)
                                    }
                                )
                                // Insertion Tool
                                if (uiState.selectedInsertComponent != null && clickMenuComponentIndex == index) {
                                    DropdownInsertionMenu(uiState, onEvent, true, {
                                        onEvent(EditorUiEvent.ShowInsertDialog(null))
                                    })
                                }
                            }

                        }
                    }
                }

                Text("Properties", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                val selectedComponent = uiState.selectedComponent
                val selectedDepthIndex = uiState.selectedComponentDepthIndex
                if (selectedComponent != null && selectedDepthIndex != null) {
                    PropertiesPanel(
                        selectedComponent = selectedComponent,
                        onUpdateProperty = { updatedComp ->
                            onEvent(EditorUiEvent.Update(selectedDepthIndex, updatedComp))
                        })
                } else {
                    Text("Select a component to edit")
                }
            }
        }
    }
}


@Composable
fun MainEditorPane(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    pageWidth: Dp,
    pageHeight: Dp,
    modifier: Modifier = Modifier
) {


    var scale by remember { mutableFloatStateOf(0.5f) }
    var offset by remember { mutableStateOf(Offset(60f, 220f)) }
    var showParent by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.4f, 3f)
                    offset += pan
                }
            }
    ) {

        if (uiState.isPdfExporting) {
            ExportComposePdf(
                template = uiState.template, onCompleted = {
                    onEvent(EditorUiEvent.PdfExporting(false))
                },
                modifier = Modifier
                    .requiredSize(pageWidth, pageHeight)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .background(Color.White)
                    .padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .requiredSize(pageWidth, pageHeight)
                    // .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .border(1.dp, Color.Gray)
                    .background(Color.White)
                    .padding(16.dp)

            ) {
                // Text("${uiState.selectedComponentDepthIndex}")
                //  Text("Editor", style = MaterialTheme.typography.titleLarge)
                //  Spacer(Modifier.height(12.dp))
                RenderEditorPage(uiState, onEvent, showParent, this)
            }
        }

        // Bottom Editor Tool
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(0.8f)
                )
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton(onClick = { showParent = !showParent }) {
                Icon(Icons.Default.BorderAll, "show parent")
            }
            Text("${offset.x.toInt()},${offset.y.toInt()}",  fontSize = 12.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = { onEvent(EditorUiEvent.PrevPage) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowLeft, "prev")
                }
                Text(
                    "P:${uiState.currentPageIndex + 1}/${uiState.template.pages.size}",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
                IconButton(onClick = { onEvent(EditorUiEvent.NextPage) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowRight, "next")
                }

            }
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Z: ${(scale * 100).toInt()}%", fontSize = 12.sp)
                IconButton(onClick = { scale = (scale - 0.1f).coerceAtLeast(0.4f) }) {
                    Icon(Icons.Default.ZoomOut, "zoomOut")
                }
                IconButton(onClick = { scale = (scale + 0.1f).coerceAtMost(3f) }) {
                    Icon(Icons.Default.ZoomIn, "zoomIn")
                }

            }

        }
    }
}

@Composable
fun DropdownInsertionMenu(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (uiState.selectedInsertComponent == null) return

    uiState.selectedComponentDepthIndex?.let {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {

            if (uiState.selectedComponent != null && uiState.selectedComponent is CVLayout) {
                DropdownMenuItem(
                    text = { Text("Insert") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onDismissRequest()
                        onEvent(
                            EditorUiEvent.Insert(
                                uiState.selectedComponentDepthIndex,
                                uiState.selectedInsertComponent
                            )
                        )
                    }
                )
            }
            DropdownMenuItem(
                text = { Text("Insert Before") },
                leadingIcon = {
                    Icon(
                        Icons.Default.ArrowUpward,
                        contentDescription = null
                    )
                },
                onClick = {
                    onDismissRequest()
                    onEvent(
                        EditorUiEvent.InsertBefore(
                            uiState.selectedComponentDepthIndex,
                            uiState.selectedInsertComponent
                        )
                    )
                }
            )
            DropdownMenuItem(
                text = { Text("Insert After") },
                leadingIcon = {
                    Icon(
                        Icons.Default.ArrowDownward,
                        contentDescription = null
                    )
                },
                onClick = {
                    onDismissRequest()
                    onEvent(
                        EditorUiEvent.InsertAfter(
                            uiState.selectedComponentDepthIndex,
                            uiState.selectedInsertComponent
                        )
                    )
                }
            )

            if (uiState.selectedInsertComponent is CVLayout && uiState.selectedInsertComponent.children.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Insert Surround") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.FormatOverline,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onDismissRequest()
                        onEvent(
                            EditorUiEvent.InsertSurround(
                                uiState.selectedComponentDepthIndex,
                                uiState.selectedInsertComponent
                            )
                        )
                    }
                )
            }

        }

    }
}

@Composable
fun ComponentTreePane(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
    isCompactAndMediumWindowSize: Boolean,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(
            onClick = onBack
        ) {
            Icon(
                if (isCompactAndMediumWindowSize) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Settings,
                "back"
            )
        }
        Text("Component Tree", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        ComponentTree(uiState, onEvent)
    }
}

@Composable
fun ComponentTree(uiState: EditorUiState, onEvent: (EditorUiEvent) -> Unit) {
    val expandedMap = rememberSaveable { mutableStateMapOf<String, Boolean>() }

    Column {
        uiState.currentPage.components.forEachIndexed { index, component ->
            ComponentTree(
                component,
                depth = index,
                componentDepth = "$index",
                selectedComponentDepthIndex = uiState.selectedComponentDepthIndex,
                expandedMap = expandedMap,
                onSelect = { depthIndex, cmp ->
                    onEvent(EditorUiEvent.SelectComponent(depthIndex, cmp))
                }
            )
        }
    }
}

@Composable
fun ComponentTree(
    component: Component,
    depth: Int,
    componentDepth: String,
    selectedComponentDepthIndex: String?,
    expandedMap: MutableMap<String, Boolean>,
    onSelect: (String, Component) -> Unit,
) {
    val isSelected = componentDepth == selectedComponentDepthIndex
    val modifier = Modifier
        .border(
            width = 1.dp,
            if (isSelected) Color.Blue else Color.Transparent
        )

    when (component) {
        is CVLayout -> {
            val key = componentDepth
            val expanded = expandedMap[key] ?: false
            Column(
                modifier = modifier
            ) {
                TreeNodeHeader(
                    modifier = Modifier
                        .background(
                            if (isSelected) Color.Blue.copy(0.1f) else Color.Transparent
                        ).padding(start = (depth * 12).dp),
                    title = component.name,
                    expanded = expanded,
                    onClick = {
                        onSelect(
                            componentDepth,
                            component
                        )
                    },
                    onToggle = { expandedMap[key] = !expanded }
                )
                if (expanded) {
                    component.children.forEachIndexed { index, cmp ->
                        ComponentTree(
                            component = cmp,
                            depth = depth + 1,
                            componentDepth = "$componentDepth $index",
                            selectedComponentDepthIndex = selectedComponentDepthIndex,
                            expandedMap = expandedMap,
                            onSelect = onSelect
                        )
                    }
                }
            }
        }

        else -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()

                    .background(
                        if (isSelected) Color.Blue.copy(0.1f) else Color.Transparent
                    )
                    .padding(start = (depth * 12).dp, top = 4.dp, bottom = 4.dp)
                    .clickable {
                        onSelect(
                            componentDepth,
                            component
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    component.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun TreeNodeHeader(
    title: String,
    expanded: Boolean,
    onClick: () -> Unit,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable {
                onToggle()
            }
        )
        Spacer(Modifier.width(6.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview
@Composable
private fun EditorPreview() {

    val template = demoCVJson.toCVTemplate()


    EditorScreen(
        uiState = EditorUiState(
            template = template,
            currentPage = template.pages[0],
        ),
        onEvent = {

        },
        onNavigateUp = {}

    )
}