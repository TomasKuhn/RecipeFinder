package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeDetail
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.domain.RecipeDetail

object RecipeDetailMapper {

    val networkToDb = object : ModelMapper<NetworkRecipeDetail, DbRecipeDetail> {

        override fun map(input: NetworkRecipeDetail): DbRecipeDetail {
            return DbRecipeDetail(
                id = input.id,
                title = input.title,
                image = input.image,
                readyInMinutes = input.readyInMinutes,
                sourceUrl = input.sourceUrl,
                likes = input.aggregateLikes,
                healthScore = input.healthScore,
                score = input.score,
                ingredients = input.ingredients.map { it.original }
            )
        }
    }

    val dbToDomain = object : ModelMapper<DbRecipeDetail, RecipeDetail> {

        override fun map(input: DbRecipeDetail): RecipeDetail {
            return RecipeDetail(
                id = input.id,
                title = input.title,
                image = input.image,
                readyInMinutes = input.readyInMinutes,
                sourceUrl = input.sourceUrl,
                likes = input.likes,
                healthScore = input.healthScore,
                score = input.score,
                ingredients = input.ingredients,
                isValid = input.isValid()
            )
        }
    }
}