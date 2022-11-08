package com.example.countriesdocker.application.usecase

import com.example.countriesdocker.application.port.out.CreateCountryRepositoryPort
import com.example.countriesdocker.application.port.usecase.CreateCountryUseCase
import com.example.countriesdocker.domain.Countries
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("Get PokemonAbility UseCase Test")
@ExtendWith(MockitoExtension::class)
class CreateCountryUseCaseTest {
    private lateinit var createCountryRepository: CreateCountryRepositoryPort
    private lateinit var createCountryUseCase: CreateCountryUseCase

    @BeforeEach
    @Throws(Exception::class)
    fun createUseCase() {
        createCountryRepository = mock(CreateCountryRepositoryPort::class.java)
        createCountryUseCase =
            CreateCountryUseCase(createCountryRepository)
    }

    @Test
    @DisplayName("When GetCountryByNameUseCase is executed Should Return a Country")
    fun createCountryOkUseCaseTest() {

        //given
        val request = COUNTRIES_DOMAIN
        val expected = COUNTRIES_EXPECTED
        `when`(createCountryRepository.createCountry(request)).thenReturn(expected)

        //when
        val response = createCountryUseCase.execute(request)

        //then
        assertEquals(expected, response)
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
        private val COUNTRIES_DOMAIN = Countries(
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
