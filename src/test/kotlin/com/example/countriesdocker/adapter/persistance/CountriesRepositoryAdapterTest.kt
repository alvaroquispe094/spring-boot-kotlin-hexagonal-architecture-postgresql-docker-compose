package com.example.countriesdocker.adapter.persistance

import com.example.countriesdocker.adapter.controller.CountriesControllerTest
import com.example.countriesdocker.config.MessageError
import com.example.countriesdocker.config.exception.ResourceNotFoundException
import com.example.countriesdocker.domain.Countries
import com.example.countriesdocker.domain.CountriesSearchFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("CountriesRepository Adapter Test")
//@Import(AppTestConfig::class)
//@RestClientTest(CountriesRepositoryAdapter::class)
//@AutoConfigureWebClient(registerRestTemplate = false)
@AutoConfigureMockMvc
@SpringBootTest
class CountriesRepositoryAdapterTest{

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val client: CountriesRepositoryAdapter? = null

    @Autowired
    private val server: MockRestServiceServer? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Test
    @DisplayName("when the getCountryByName is called, the adapter must return a EmptyOrNullBodyRestClientException")
    fun getCountryByNameNotFoundException() {

      /*  // given
        val countryName = "Australia"
        val URL: String = "/api/v1/countries/name/${countryName}"

        // when
        Mockito.`when`(findCountryByNameInPort!!.execute(ArgumentMatchers.anyString()))
            .thenThrow(
                ResourceNotFoundException(
                    MessageError.RESOURCE_NOT_FOUND.errorCode,
                    MessageError.RESOURCE_NOT_FOUND.defaultMessage
                )
            )

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(MessageError.RESOURCE_NOT_FOUND.defaultMessage)))
        */
        val countryNames = "Australia"
        val URL: String = "/api/v1/countries/name/${countryNames}"

        Mockito.`when`(client?.findCountryByName(ArgumentMatchers.anyString()))
            .thenThrow(
                ResourceNotFoundException(
                    MessageError.RESOURCE_NOT_FOUND.errorCode,
                    MessageError.RESOURCE_NOT_FOUND.defaultMessage
                )
            )

        //val thrown2 = Assertions.catchThrowable {
            mockMvc!!.perform(
                MockMvcRequestBuilders.get(URL)
                    .contentType(MediaType.APPLICATION_JSON))
        //}
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(MessageError.RESOURCE_NOT_FOUND.defaultMessage)))
        //then
        /*assertThat(thrown2)
            .isInstanceOf(ResourceNotFoundException::class.java)
            .hasMessage("No se encontro el country con name = $countryNames")
*/
        //given
       /* val countryName = "Australia"
        server?.expect(requestTo("/api/v1/countries/name/${countryName}"))
            ?.andRespond(withStatus(HttpStatus.NOT_FOUND))

        //when
        val thrown = Assertions.catchThrowable { client?.findCountryByName(countryName) }

        //then
        assertThat(thrown)
            .isInstanceOf(ResourceNotFoundException::class.java)
            .hasMessage("No se encontro el country con name = $countryName")
    */
    }


    @Test
    @DisplayName("when the getCountry is called by name, the adapter must return a Pokemon domain object")
    fun getCountryByNameNormalCase() {

        // given
        val countryNames = "Australia"
        val mockedResponse: Countries = mockedResponse
        val expectedResponses: Countries = expectedDomainForMockedResponse
        val detailsStrings = objectMapper!!.writeValueAsString(mockedResponse)
        val URL: String = "/api/v1/countries/name/${countryNames}"

        // when
        `when`(client?.findCountryByName(ArgumentMatchers.anyString()))
            .thenReturn(mockedResponse)

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(expectedResponses.name)))
        //given
        /*val countryName = "Australia"
        val expectedResponse: Countries = expectedDomainForMockedResponse
        val detailsString = objectMapper!!.writeValueAsString(mockedResponse)

        server?.expect(requestTo("/api/v1/countries/name/Australia"))
            ?.andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON))

        //when
        val currentResponse: Countries? = client?.findCountryByName("Australia")

        //then
        assertThat(currentResponse).isEqualTo(expectedResponse)*/
    }

    @Test
    @DisplayName("when the getCountries is called by filters , the adapter must return a list of Countries")
    fun getCountriesByFiltersNormalCase() {

        // given
        val countryNames = "Australia"
        val mockedResponse: List<Countries> = mockedListResponse
        val expectedResponses: List<Countries> = expectedDomainForMockedListResponse
        val mockedRequest:CountriesSearchFilter = mockedRequest
        val detailsStrings = objectMapper!!.writeValueAsString(mockedResponse)
        val URL: String = "/api/v1/countries/search"

        // when
        `when`(client?.searchCountry(mockedRequest))
            .thenReturn(mockedListResponse)

        // expect
        mockMvc!!.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(mockedRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper!!.writeValueAsString(expectedResponses)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[*].language", Matchers.`is`(expectedResponses.map { it.language })))

    }

    private val mockedResponse: Countries
        get() {
            return Countries(
                name = "Australia",
                capital = "Canberra",
                subregion = "Australia and New Zealand",
                region = "Oceania",
                population = 25687041,
                area = 76920240.0,
                currency = "Australian dollar",
                language = "English",
                flag = "https://flagcdn.com/au.svg")
        }

    private val expectedDomainForMockedResponse: Countries
        get() {
            return Countries( name = "Australia",
                capital = "Canberra",
                subregion = "Australia and New Zealand",
                region = "Oceania",
                population = 25687041,
                area = 76920240.0,
                currency = "Australian dollar",
                language = "English",
                flag = "https://flagcdn.com/au.svg")
        }

    private val mockedRequest: CountriesSearchFilter
        get() {
            return CountriesSearchFilter(
                name = null,
                subregion = null,
                region = null,
                populationDesde = null,
                populationHasta = null,
                areaDesde = null,
                areaHasta = null,
                currency = null,
                language = "English"
            )
        }

    private val mockedListResponse: List<Countries>
        get() {
            return listOf(
                Countries(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
                Countries(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
            )
        }

    private val expectedDomainForMockedListResponse: List<Countries>
        get() {
            return listOf(
                Countries(name = "Australia", capital = "Canberra", subregion = "Australia and New Zealand", region = "Oceania2", population = 25687041, area = 7692024.0, currency = "Australian dollar", language = "English", flag = "https://flagcdn.com/au.svg"),
                Countries(name = "American Samoa", capital = "Pago Pago", subregion = "Polynesia", region = "Oceania", population = 55197, area = 199.0, currency = "United States Dollar", language = "English", flag = "https://flagcdn.com/as.svg")
            )
        }

}