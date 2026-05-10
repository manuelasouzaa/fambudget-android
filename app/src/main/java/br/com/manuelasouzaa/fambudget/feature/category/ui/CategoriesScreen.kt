package br.com.manuelasouzaa.fambudget.feature.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryBlue
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryBrown
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryGray
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryGreen
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryOrange
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryPink
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryPurple
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryYellow
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel
import br.com.manuelasouzaa.fambudget.feature.category.ui.viewmodel.CategoriesScreenViewModel
import br.com.manuelasouzaa.fambudget.feature.category.ui.viewmodel.CategoriesUiState
import org.koin.androidx.compose.koinViewModel

private val availableColors = listOf(
    categoryBrown, categoryOrange, categoryPink, categoryPurple,
    categoryGray, categoryBlue, categoryGreen, categoryYellow
)

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    val viewModel: CategoriesScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showCreateDialog by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<CategoryUiModel?>(null) }
    var deleteTarget by remember { mutableStateOf<CategoryUiModel?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            CategoriesUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize()
            )
            CategoriesUiState.Error -> Text(
                text = stringResource(R.string.error_unknown),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
            is CategoriesUiState.Success -> {
                CategoriesGrid(
                    defaultCategories = state.defaultCategories,
                    userCategories = state.userCategories,
                    onEdit = { editTarget = it },
                    onDelete = { deleteTarget = it }
                )
            }
        }

        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null
            )
        }
    }

    if (showCreateDialog) {
        CategoryFormDialog(
            title = stringResource(R.string.create_category_title),
            onConfirm = { name, color ->
                viewModel.addCategory(name, color)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false }
        )
    }

    editTarget?.let { category ->
        CategoryFormDialog(
            key = category.id,
            title = stringResource(R.string.edit_category_title),
            initialName = category.name,
            initialColor = category.color,
            onConfirm = { name, color ->
                viewModel.editCategory(category.id, name, color)
                editTarget = null
            },
            onDismiss = { editTarget = null }
        )
    }

    deleteTarget?.let { category ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = { Text(stringResource(R.string.delete_category_title)) },
            text = { Text(stringResource(R.string.delete_category_message)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteCategory(category.id)
                    deleteTarget = null
                }) { Text(stringResource(R.string.confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun CategoriesGrid(
    defaultCategories: List<CategoryUiModel>,
    userCategories: List<CategoryUiModel>,
    onEdit: (CategoryUiModel) -> Unit,
    onDelete: (CategoryUiModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(R.string.default_categories_section),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        itemsIndexed(defaultCategories) { _, category ->
            CategoryCard(category = category, isEditable = false)
        }
        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(R.string.user_categories_section),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        if (userCategories.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(R.string.no_user_categories),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        itemsIndexed(userCategories) { _, category ->
            CategoryCard(
                category = category,
                isEditable = true,
                onEdit = { onEdit(category) },
                onDelete = { onDelete(category) }
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: CategoryUiModel,
    isEditable: Boolean,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = category.color.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp).height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (isEditable) {
                Box {
                    IconButton(onClick = { showMenu = true }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.edit_category_title)) },
                            onClick = { showMenu = false; onEdit() }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.delete_category_title)) },
                            onClick = { showMenu = false; onDelete() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryFormDialog(
    key: Any? = null,
    title: String,
    initialName: String = "",
    initialColor: Color = availableColors.first(),
    onConfirm: (String, Color) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember(key, initialName) { mutableStateOf(initialName) }
    var selectedColor by remember(key, initialColor) { mutableStateOf(initialColor) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(text = stringResource(R.string.color_label), style = MaterialTheme.typography.bodyMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 4
                ) {
                    availableColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(color)
                                .then(
                                    if (color == selectedColor)
                                        Modifier.border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                                    else Modifier
                                )
                                .clickable { selectedColor = color }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name, selectedColor) }
            ) { Text(stringResource(R.string.save_title)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        }
    )
}
