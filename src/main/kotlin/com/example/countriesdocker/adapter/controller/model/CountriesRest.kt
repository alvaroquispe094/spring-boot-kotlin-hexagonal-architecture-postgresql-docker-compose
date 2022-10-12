package com.example.countriesdocker.adapter.controller.model

import com.example.countriesdocker.adapter.persistance.model.CountriesEntity
import com.example.countriesdocker.domain.Countries
import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class CountriesRest(
    @get:NotBlank val name: String,
    @get:Valid val capital: String?,
    @get:Valid val subregion: String,
    @get:Valid val region: String,
    @get:Valid val population: Int,
    @get:Valid val area: Float,
    @get:Valid val currency: String?,
    @get:Valid val language: String,
    @get:Valid val flag: String,
) {

    fun toDomain() =
        Countries(
            name = name,
            capital = capital,
            subregion = subregion,
            region = region,
            population = population,
            area = area,
            currency = currency,
            language = language,
            flag = flag,
        )

    companion object {
        infix fun from(countries: Countries): CountriesRest {
            return CountriesRest(
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
}