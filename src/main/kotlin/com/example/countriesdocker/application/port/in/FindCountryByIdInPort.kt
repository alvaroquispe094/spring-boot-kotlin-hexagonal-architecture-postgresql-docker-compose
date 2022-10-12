package com.example.countriesdocker.application.port.`in`

import com.example.countriesdocker.domain.Countries
import java.util.concurrent.CompletionStage

interface FindCountryByIdInPort {
    fun find(id: Long): CompletionStage<Countries>
}