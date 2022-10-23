package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries

interface CreateCountryInPort {
    fun execute(countries: Countries): Countries
}