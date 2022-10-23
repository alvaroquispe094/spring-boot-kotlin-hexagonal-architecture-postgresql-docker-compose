package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries

interface FindCountryByNameInPort {
    fun execute(name: String): Countries
}