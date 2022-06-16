package com.jHerscu.clearskies.domain.useCase.weather

import com.jHerscu.clearskies.data.repo.FakeWeatherRepoImpl
import org.junit.Before

class CacheWeatherUseCaseTest {
    private lateinit var useCase: CacheWeatherUseCase
    private lateinit var repo: FakeWeatherRepoImpl

    @Before
    fun setUp() {
        repo = FakeWeatherRepoImpl()
        useCase = CacheWeatherUseCase(repo)
    }
}