package com.example.countriesdocker.adapter.controller.model

import com.example.countriesdocker.domain.CountriesSearchFilter
import javax.validation.constraints.NotBlank

data class CountriesSearchFilterRest(
    @get:NotBlank val name: String?,
    val subregion: String?,
    val region: String?,
    val populationDesde: Int?,
    val populationHasta: Int?,
    val areaDesde: Number?,
    val areaHasta: Number?,
    val currency: String?,
    val language: String?,
) {
    fun toDomain() =
        CountriesSearchFilter(
            name = name,
            subregion = subregion,
            region = region,
            populationDesde = populationDesde,
            populationHasta = populationHasta,
            areaDesde = areaDesde,
            areaHasta = areaHasta,
            currency = currency,
            language = language,
        )
}