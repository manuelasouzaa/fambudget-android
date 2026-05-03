package br.com.manuelasouzaa.fambudget.feature.category.data.mapper

import androidx.compose.ui.graphics.Color
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryBlue
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryBrown
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryGray
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryGreen
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryOrange
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryPink
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryPurple
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryYellow

enum class CategoryColorMapper(val value: String) {

    BROWN("brown"),
    ORANGE("orange"),
    PINK("pink"),
    PURPLE("purple"),
    GRAY("gray"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    DEFAULT("default");

    fun toUiColor(): Color {
        return when (this) {
            BROWN -> categoryBrown
            ORANGE -> categoryOrange
            PINK -> categoryPink
            PURPLE -> categoryPurple
            GRAY, DEFAULT -> categoryGray
            BLUE -> categoryBlue
            GREEN -> categoryGreen
            YELLOW -> categoryYellow
        }
    }

    companion object {
        fun fromValue(value: String) = entries.find { it.value == value } ?: DEFAULT

        fun fromUiColor(color: Color) =
            entries.find { it != DEFAULT && it.toUiColor() == color }?.value ?: DEFAULT.value
    }

}
