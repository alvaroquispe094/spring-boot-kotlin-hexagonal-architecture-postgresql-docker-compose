package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries

interface FindAllCountriesInPort {
    fun find(): List<Countries>
}