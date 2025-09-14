package com.coderbdk.cvbuilder.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.coderbdk.cvbuilder.util.toJsonString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToEditor: (String) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        FilledIconButton(
            onClick = {
                onNavigateToEditor(uiState.templates[uiState.selectedIndex ?: 0].toJsonString())
            },
            enabled = uiState.selectedIndex != null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.AddCircle, "create")
        }

        LazyVerticalGrid(columns = GridCells.Adaptive(400.dp), Modifier.fillMaxSize()) {
            itemsIndexed(uiState.templates) { index, template ->
                OutlinedCard(
                    Modifier
                        .requiredSize(400.dp, 500.dp)
                        .padding(8.dp),
                    border = if (uiState.selectedIndex != null && uiState.selectedIndex == index) BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ) else CardDefaults.outlinedCardBorder()
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                onEvent(HomeUiEvent.SelectTemplate(index = index))
                            }
                    ) {
                        template.imageResThumb?.let {
                            Image(
                                painter = painterResource(template.imageResThumb as DrawableResource),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(
                                    RoundedCornerShape(8.dp)
                                )
                            )
                        }
                        Text(
                            template.name, Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                                )
                                .align(Alignment.TopCenter)
                                .padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }

            }
        }
    }

}

