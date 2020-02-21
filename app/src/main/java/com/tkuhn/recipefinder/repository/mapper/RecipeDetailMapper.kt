package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeDetail
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeDetail
import com.tkuhn.recipefinder.domain.RecipeDetail

object RecipeDetailMapper {

    val networkToDb = object : ModelMapper<NetworkRecipeDetail, DbRecipeDetail> {

        override fun map(input: NetworkRecipeDetail): DbRecipeDetail {
            return DbRecipeDetail(
                input.id,
                input.title
            )
        }
    }

    val dbToDomain = object : ModelMapper<DbRecipeDetail, RecipeDetail> {

        override fun map(input: DbRecipeDetail): RecipeDetail {
            return RecipeDetail(
                input.id,
                input.title,
                input.isValid()
            )
        }
    }
}