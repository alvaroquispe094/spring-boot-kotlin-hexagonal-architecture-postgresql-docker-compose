package com.example.countriesdocker.adapter.persistance.mapper

import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import com.example.countriesdocker.domain.Countries

object CountriesMapper {

    fun toCountriesEntity(countries: Countries): CountriesEntity {
        return CountriesEntity(
            id = null,
            name = countries.name,
            capital = countries.capital,
            subregion = countries.subregion,
            region = countries.region,
            population = countries.population,
            area = countries.area,
            currency = countries.currency,
            language = countries.language,
            flag = countries.flag,
        )
    }
}