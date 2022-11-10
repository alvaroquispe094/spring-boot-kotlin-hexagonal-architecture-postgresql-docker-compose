package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter

interface SearchFilterCountryRepositoryPort {
    fun searchCountry(searchFilter: CountriesSearchFilter): List<Countries>
}