/*
package com.example.countriesdocker.adapter.config

import com.example.countriesdocker.CountriesDockerApplication
import com.example.countriesdocker.adapter.controller.CountriesController
import com.example.countriesdocker.adapter.persistance.CountriesRepositoryAdapter
import com.example.countriesdocker.adapter.persistance.repository.SpringDataCountriesRepository
import com.example.countriesdocker.application.port.`in`.FindAllCountriesInPort
import com.example.countriesdocker.application.port.out.CountriesRepositoryPort
import com.example.countriesdocker.application.port.usecase.GetAllCountriesUseCase
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan(basePackageClasses = [CountriesDockerApplication::class])
class BeanConfiguration(
                       ) : WebMvcConfigurer {

   */
/* companion object {
        private const val CORE_POOL_SIZE = 20
        private const val MAX_POOL_SIZE = 1000
        private const val ASYNC_PREFIX = "async-"
        private const val WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN = true
    }*//*


    */
/*@Bean("asyncExecutor")
    fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = CORE_POOL_SIZE
        executor.maxPoolSize = MAX_POOL_SIZE
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASK_TO_COMPLETE_ON_SHUTDOWN)
        executor.setThreadNamePrefix(ASYNC_PREFIX)
        executor.initialize()
        return LazyTraceExecutor(beanFactory, executor)
    }*//*


    */
/*@Bean
    fun countriesRepositoryAdapter(repository: SpringDataCountriesRepository): CountriesRepositoryAdapter? {
        return CountriesRepositoryAdapter(repository)
    }*//*


    */
/*@Bean
    fun countriesRepositoryAdapter(repository: SpringDataCountriesRepository): CountriesRepositoryAdapter {
        return CountriesRepositoryAdapter(repository)
    }*//*

    */
/*@Bean
    fun getAllCountriesUseCase(countriesRepositoryPort: CountriesRepositoryPort): GetAllCountriesUseCase{
        return GetAllCountriesUseCase(countriesRepositoryPort)
    }*//*


   */
/* @Bean
    fun countriesController(findAllCountriesInPort: FindAllCountriesInPort): CountriesController {
        return CountriesController(findAllCountriesInPort)
    }*//*


}

*/
