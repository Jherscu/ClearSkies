package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.repo.FakeWeatherRepoImpl
import com.jHerscu.clearskies.utils.mappers.FakeWeatherForecastMapper
import org.junit.Before

class GetWeatherDataUseCaseTest {
    private lateinit var useCase: GetWeatherDataUseCase
    private lateinit var repo: FakeWeatherRepoImpl
    private lateinit var mapper: FakeWeatherForecastMapper

    @Before
    fun setUp() {
        mapper = FakeWeatherForecastMapper()
        repo = FakeWeatherRepoImpl()
        useCase = GetWeatherDataUseCase(repo, mapper)
    }
}