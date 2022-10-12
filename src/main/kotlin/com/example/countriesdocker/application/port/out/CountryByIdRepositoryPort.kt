package com.example.countriesdocker.application.port.out

import com.example.countriesdocker.domain.Countries
import java.util.concurrent.CompletionStage

interface CountryByIdRepositoryPort {
    fun findCountryById(id: Long): Countries
}