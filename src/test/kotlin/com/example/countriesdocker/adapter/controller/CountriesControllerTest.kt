package com.example.countriesdocker.adapter.controller

import com.example.countriesdocker.application.port.`in`.FindCountryByNameInPort
import com.example.countriesdocker.domain.Countries
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("CountriesController Adapter Test")
@AutoConfigureMockMvc
@SpringBootTest
class CountriesControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val findCountryByNameInPort: FindCountryByNameInPort? = null

    @Test
    @DisplayName("when you get a country by Id, the adapter must return a country")
    fun getCountryById() {

        // given
        val countryName = "Australia"

        // when
        `when`(findCountryByNameInPort!!.execute(anyString()))
            .thenReturn(
                Countries(
                    name = "Australia",
                    capital = "Canberra",
                    subregion = "Australia and New Zealand",
                    region = "Oceania2",
                    population = 25687041,
                    area = 76920240F,
                    currency = "Australian dollar",
                    language = "English",
                    flag = "https://flagcdn.com/au.svg"
                )
            )

        // expect
        mockMvc?.perform(
            MockMvcRequestBuilders.get("/api/v1/countries/name/${countryName}")
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)

    }

}