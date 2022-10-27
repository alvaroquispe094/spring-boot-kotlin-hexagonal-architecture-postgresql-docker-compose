package com.example.countriesdocker.application.port.out

interface DeleteRepositoryPort {
    fun deleteCountry(countryId: Long)
}