package com.example.countriesdocker.adapter.persistance.model

import com.example.countriesdocker.domain.Countries
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Entity
@Table(name="Countries")
data class CountriesEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val name: String,
    val capital: String?,
    val subregion: String,
    val region: String,
    val population: Int,
    val area: Double,
    val currency: String?,
    val language: String,
    val flag: String,
){

    fun toCountriesDomain(): Countries {
        return Countries(
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
    }

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
