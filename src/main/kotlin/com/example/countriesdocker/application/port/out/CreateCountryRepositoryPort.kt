package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries

interface CreateCountryRepositoryPort {
    fun createCountry(countries: Countries): Countries
}