package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.repo.FakeWeatherRepoImpl
import org.junit.Before

class FetchAllWeatherUseCaseTest {
    private lateinit var useCase: FetchAllWeatherUseCase
    private lateinit var repo: FakeWeatherRepoImpl

    @Before
    fun setUp() {
        repo = FakeWeatherRepoImpl()
        useCase = FetchAllWeatherUseCase(repo)
    }
}