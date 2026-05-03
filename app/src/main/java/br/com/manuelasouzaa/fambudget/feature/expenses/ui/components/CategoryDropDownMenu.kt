package br.com.manuelasouzaa.fambudget.feature.expenses.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDownMenu(
    onCategorySelected: (CategoryUiModel) -> Unit,
    categories: CategorySections
) {
    if (categories.default.isEmpty()) return
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(categories.default[0]) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selectedItem.name,
            onValueChange = {},
            readOnly = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_unfold_more),
                    contentDescription = null
                )
            }
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            categories.default.forEach { model ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            text = model.name,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    onClick = {
                        isExpanded = false
                        selectedItem = model
                        onCategorySelected(model)
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_circle),
                            tint = model.color,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}
