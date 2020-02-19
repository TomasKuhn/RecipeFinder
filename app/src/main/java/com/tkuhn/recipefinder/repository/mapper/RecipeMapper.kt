package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipe
import com.tkuhn.recipefinder.domain.Recipe

class RecipeMapper {

    val networkToDomain = object : ModelMapper<NetworkRecipe, Recipe> {

        override fun map(input: NetworkRecipe): Recipe {
            return Recipe(
                input.id,
                input.title
            )
        }
    }
}