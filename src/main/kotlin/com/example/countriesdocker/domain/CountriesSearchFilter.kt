package com.example.countriesdocker.domain

data class CountriesSearchFilter(
    val name: String?,
    val subregion: String?,
    val region: String?,
    val populationDesde: Int?,
    val populationHasta: Int?,
    val areaDesde: Number?,
    val areaHasta: Number?,
    val currency: String?,
    val language: String?,
)
