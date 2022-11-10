package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter

interface SearchFilterCountryInPort {
    fun execute(searchFilter: CountriesSearchFilter): List<Countries>
}