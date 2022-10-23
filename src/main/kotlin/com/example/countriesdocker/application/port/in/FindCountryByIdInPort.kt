package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries

interface FindCountryByIdInPort {
    fun execute(id: Long): Countries
}