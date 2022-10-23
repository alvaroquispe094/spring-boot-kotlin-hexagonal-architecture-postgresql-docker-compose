package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries

interface CountryByIdRepositoryPort {
    fun findCountryById(countryId: Long): Countries
}