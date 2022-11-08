package com.example.countriesdocker.application.usecase

import com.example.countriesdocker.application.port.out.CountryByNameRepositoryPort
import com.example.countriesdocker.application.port.usecase.GetCountryByNameUseCase
import com.example.countriesdocker.domain.Countries
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("Get PokemonAbility UseCase Test")
@ExtendWith(MockitoExtension::class)
class GetCountryByNameUseCaseTest {
    private val countryByNameRepository: CountryByNameRepositoryPort = mock(CountryByNameRepositoryPort::class.java)

    @Test
    @DisplayName("When GetCountryByNameUseCase is executed Should Return a Country")
    fun countryByNameUseCaseTest() {

        //given
        val pokemonName = "pokemonName"
        val expected = COUNTRIES_EXPECTED

        `when`(countryByNameRepository.findCountryByName(anyString())).thenReturn(expected)
        val countryByNameUseCase = GetCountryByNameUseCase(countryByNameRepository)

        //when
        val countryViewActual: Countries = countryByNameUseCase.execute(pokemonName)

        //then
        assertEquals(expected, countryViewActual)
    }

    companion object {
        private val COUNTRIES_EXPECTED = Countries(
            name = "Australia",
            capital = "Canberra",
            subregion = "Australia and New Zealand",
            region = "Oceania2",
            population = 25687041,
            area = 7692024.0,
            currency = "Australian dollar",
            language = "English",
            flag = "https://flagcdn.com/au.svg"
        )

    }

}
