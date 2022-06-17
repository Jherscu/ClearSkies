package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.utils.mappers.FakeWeatherForecastMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before

class MapWeatherToLocalUseCaseTest {
    private lateinit var useCase: MapWeatherToLocalUseCase
    private lateinit var mapper: FakeWeatherForecastMapper
    private lateinit var dispatcher: CoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        mapper = FakeWeatherForecastMapper()
        mapper.setUp()
        useCase = MapWeatherToLocalUseCase(mapper, dispatcher)
    }
}