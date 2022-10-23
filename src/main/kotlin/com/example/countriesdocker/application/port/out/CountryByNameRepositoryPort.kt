package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries

interface CountryByNameRepositoryPort {
    fun findCountryByName(name: String): Countries
}