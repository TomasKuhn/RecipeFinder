package com.tkuhn.recipefinder.repository.mapper

import com.tkuhn.recipefinder.datasource.database.dto.DbForecast
import com.tkuhn.recipefinder.datasource.network.dto.NetworkForecast
import com.tkuhn.recipefinder.domain.Forecast


class ForecastMappers {

    val dbToDomain = object : ModelMapper<DbForecast, Forecast> {

        override fun map(input: DbForecast): Forecast {
            return Forecast(
                input.cod,
                "Forecas id is ${input.cod}"
            )
        }
    }

    val networkToDb = object : ModelMapper<NetworkForecast, DbForecast> {

        override fun map(input: NetworkForecast): DbForecast {
            return DbForecast(
                input.cod
            )
        }
    }

}