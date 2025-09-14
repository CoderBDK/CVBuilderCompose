package com.coderbdk.cvbuilder.ui.editor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.coderbdk.cvbuilder.data.model.BoolProperty
import com.coderbdk.cvbuilder.data.model.CVImage
import com.coderbdk.cvbuilder.data.model.CVList
import com.coderbdk.cvbuilder.data.model.Component
import com.coderbdk.cvbuilder.data.model.ComponentProperty
import com.coderbdk.cvbuilder.data.model.EnumProperty
import com.coderbdk.cvbuilder.data.model.FloatProperty
import com.coderbdk.cvbuilder.data.model.Hintable
import com.coderbdk.cvbuilder.data.model.IntProperty
import com.coderbdk.cvbuilder.data.model.StringProperty
import com.coderbdk.cvbuilder.getPlatform
import com.coderbdk.cvbuilder.util.updateLayoutProperty
import com.coderbdk.cvbuilder.util.updateProperty
import kotlinx.coroutines.launch

@Composable
fun PropertiesPanel(
    selectedComponent: Component,
    onUpdateProperty: (Component) -> Unit
) {
    var layoutExpanded by remember { mutableStateOf(true) }
    var componentExpanded by remember { mutableStateOf(true) }
    var componentContentExpand by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(
            title = "Layout",
            expanded = layoutExpanded,
            onToggle = { layoutExpanded = !layoutExpanded }
        )
        AnimatedVisibility(visible = layoutExpanded) {
            Column {
                Properties(
                    properties = selectedComponent?.layoutProperties?.properties,
                    onUpdateProperty = { propertyName, newValue ->
                        selectedComponent?.let { comp ->
                            val updatedComp = comp.updateLayoutProperty(propertyName, newValue)
                            onUpdateProperty(updatedComp)
                        }
                    })
            }
        }
        SectionHeader(
            title = "Others",
            expanded = componentExpanded,
            onToggle = { componentExpanded = !componentExpanded }
        )
        AnimatedVisibility(visible = componentExpanded) {
            Column {
                Properties(
                    properties = selectedComponent?.properties,
                    onUpdateProperty = { propertyName, newValue ->
                        selectedComponent?.let { comp ->
                            val updatedComp = comp.updateProperty(propertyName, newValue)
                            onUpdateProperty(updatedComp)
                        }
                    })
            }
        }

        SectionHeader(
            title = "Content",
            expanded = componentContentExpand,
            onToggle = { componentContentExpand = !componentContentExpand }
        )
        AnimatedVisibility(visible = componentContentExpand) {
            Column {
                when (selectedComponent) {
                    is CVList -> {
                        EditContentList(selectedComponent, onUpdateProperty)
                    }

                    is CVImage -> {
                        EditContentImage(selectedComponent, onUpdateProperty)
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null
        )
        Spacer(Modifier.width(6.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
    }
}


@Composable
fun Properties(
    properties: Map<String, ComponentProperty>?,
    onUpdateProperty: (String, Any) -> Unit
) {

    properties?.forEach { (_, prop) ->
        when (prop) {
            is StringProperty -> {
                OutlinedTextField(
                    value = prop.value,
                    onValueChange = { onUpdateProperty(prop.name, it) },
                    label = { Text(prop.name) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is IntProperty -> {
                var value by remember(prop.value) { mutableStateOf(prop.value.toString()) }
                OutlinedTextField(
                    value = value,
                    onValueChange = { newVal ->
                        value = newVal
                        newVal.toIntOrNull()?.let { onUpdateProperty(prop.name, it) }
                    },
                    label = { Text(prop.name) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is FloatProperty -> {
                var value by remember(prop.value) { mutableStateOf(prop.value.toString()) }
                OutlinedTextField(
                    value = value,
                    onValueChange = { newVal ->
                        value = newVal
                        newVal.toFloatOrNull()?.let { onUpdateProperty(prop.name, it) }
                    },
                    label = { Text(prop.name) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is EnumProperty<*> -> {
                CVDropdownMenu(
                    value = prop.value.toString(),
                    items = prop.options,
                    onItemSelected = { option ->
                        onUpdateProperty(prop.name, option)
                    },
                    label = {
                        Text(prop.name)
                    },
                    itemContent = { option ->
                        if (option is Hintable) {
                            Text("$option (${option.hint})")
                        } else {
                            Text(option.toString())
                        }
                    }
                )

            }

            is BoolProperty -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = prop.value,
                        onCheckedChange = { onUpdateProperty(prop.name, it) }
                    )
                    Text(prop.name)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun <T> CVDropdownMenu(
    value: String,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    label: @Composable () -> Unit,
    itemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { itemContent(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ColumnScope.EditContentList(
    component: CVList,
    onUpdateProperty: (Component) -> Unit
) {
    val items = remember(component) { component.items.toMutableStateList() }
    items.forEachIndexed { index, text ->
        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                items[index] = newValue
                onUpdateProperty(component.copy(items = items.toList()))
            },
            label = { Text("Item ${index + 1}") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    items.removeAt(index)
                    onUpdateProperty(component.copy(items = items.toList()))
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))

    if (items.size < 25) {
        Button(
            onClick = {
                items.add("")
                onUpdateProperty(component.copy(items = items.toList()))
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Item")
        }
    }
}

@Composable
fun EditContentImage(component: CVImage, onUpdateProperty: (Component) -> Unit) {
    val coroutine = rememberCoroutineScope()
    Button(
        onClick = {
            coroutine.launch {
                val data = getPlatform().imagePicker.pickImage()
                onUpdateProperty(
                    component.copy(
                        data = data
                    )
                )
            }
        }
    ) {
        Text("Pick Image")
    }
}
