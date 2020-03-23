package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.domain.Recipe

object RecipeMapper {

    val networkToDomain = object : ModelMapper<NetworkRecipe, Recipe> {
        override fun map(input: NetworkRecipe): Recipe {
            return Recipe(
                id = input.id,
                title = input.title,
                imageUrl = input.image,
                calories = input.calories,
                carbs = input.carbs,
                fat = input.fat,
                protein = input.protein
            )
        }
    }
    val networkToDomainList = networkToDomain.toListMapper()
}

fun NetworkRecipe.toRecipe() = RecipeMapper.networkToDomain.map(this)