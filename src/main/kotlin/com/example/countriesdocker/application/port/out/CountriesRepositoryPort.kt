package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries

interface CountriesRepositoryPort {
    fun findAllCountries(): List<Countries>
}