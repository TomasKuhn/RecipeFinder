package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeSummary
import com.tkuhn.recipefinder.datasource.network.dto.NetworkRecipeSummary
import com.tkuhn.recipefinder.domain.RecipeSummary

object RecipeSummaryMapper {

    val networkToDb = object : ModelMapper<NetworkRecipeSummary, DbRecipeSummary> {

        override fun map(input: NetworkRecipeSummary): DbRecipeSummary {
            return DbRecipeSummary(
                id = input.id,
                title = input.title,
                summary = input.summary
            )
        }
    }

    val dbToDomain = object : ModelMapper<DbRecipeSummary, RecipeSummary> {

        override fun map(input: DbRecipeSummary): RecipeSummary {
            return RecipeSummary(
                title = input.title,
                summary = input.summary,
                isValid = input.isValid()
            )
        }
    }
}