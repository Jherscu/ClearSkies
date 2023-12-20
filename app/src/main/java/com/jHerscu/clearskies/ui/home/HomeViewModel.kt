package com.jHerscu.clearskies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.data.model.Forecast
import com.jHerscu.clearskies.data.model.response.UnparsedResponsesHolder
import com.jHerscu.clearskies.data.source.local.entity.LocalDailyForecast
import com.jHerscu.clearskies.data.source.local.entity.LocalHourlyForecast
import com.jHerscu.clearskies.di.MainDispatcher
import com.jHerscu.clearskies.domain.useCase.location.GetCityByNameUseCase
import com.jHerscu.clearskies.domain.useCase.weather.WeatherUseCases
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.SimpleResource
import com.jHerscu.clearskies.utils.TextWrapper
import com.jHerscu.clearskies.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getCityByNameUseCase: GetCityByNameUseCase,
        private val weatherUseCases: WeatherUseCases,
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private lateinit var city: String

        private val _eventFlow = MutableSharedFlow<UiEvent>()
        val eventFlow = _eventFlow.asSharedFlow()

        private val errorChannel = Channel<TextWrapper>() // TODO(jherscu): SHARED FLOW OR BOILERPLATE STATE IN VmState
        val errorFlow = errorChannel.receiveAsFlow()

        private val _weatherData =
            MutableLiveData(listOf<Forecast>()) // TODO() Set Error placeholder data so views can be populated
        val weatherData: LiveData<List<Forecast>> get() = _weatherData

        private val _weatherIsRecent = MutableLiveData(false)
        val weatherIsRecent: LiveData<Boolean> get() = _weatherIsRecent // TODO(jherscu): STATEFLOW

        private val _weatherInCache = MutableLiveData(false)
        val weatherInCache: LiveData<Boolean> get() = _weatherInCache

        init {
            viewModelScope.launch(mainDispatcher) { // TODO(jherscu): MAIN IMMEDIATE

                val loadDataJob =
                    coroutineScope {

                        launch {
                            weatherUseCases.validateWeatherUpToDateUseCase(
                                qualifiedName = city,
                                currentDate = 1,
                            ).collect { cityUpToDate ->
                                _weatherIsRecent.value = cityUpToDate
                            }
                        }

                        launch {
                            weatherUseCases.validateWeatherExistsUseCase(
                                qualifiedName = city,
                            ).collect { weatherExists ->
                                _weatherInCache.value = weatherExists
                            }
                        }
                    }

                loadDataJob.join()
                loadWeather()
            }
        }

        // TODO(jherscu): Call from fragment init
        fun setCity(qualifiedName: String) {
            city = qualifiedName
        }

        fun loadWeather() {
            viewModelScope.launch(mainDispatcher) { // TODO(jherscu): MAIN IMMEDIATE

                cachePipeline@ while (weatherInCache.value == false || weatherIsRecent.value == false) {
                    // Fetch weather from client
                    val responseResource: Resource<UnparsedResponsesHolder?> =
                        try {
                            weatherUseCases.fetchAllWeatherUseCase(
                                getCityByNameUseCase(city),
                            )
                        } catch (e: HttpException) {
                            errorChannel.send(TextWrapper.DynamicString(e.message()))
                            Resource.Error(text = TextWrapper.StringResource(R.string.bad_response))
                        } catch (e: IOException) {
                            errorChannel.send(TextWrapper.DynamicString(e.stackTraceToString()))
                            Resource.Error(text = TextWrapper.StringResource(R.string.bad_connection))
                        }

                    // Map successful response or report fetching error and break loop
                    // TODO(jherscu): typeAlias?
                    val mappingResource: Resource<Pair<List<LocalDailyForecast>, List<LocalHourlyForecast>>?> =
                        when (responseResource) {
                            is Resource.Success -> {
                                with(responseResource.data!!) {
                                    weatherUseCases.mapWeatherToLocalUseCase(
                                        dailyAndHourlyWeatherResponse = dailyAndHourlyWeatherResponse,
                                        yesterdaysWeatherResponse = yesterdaysWeatherResponse,
                                        qualifiedName = city,
                                    )
                                }
                            }
                            is Resource.Error -> { // TODO(jherscu): Collectors can check if is TextWrapper.DynamicString or .StringResource
                                _eventFlow.emit(UiEvent.ShowSnackbar(TextWrapper.StringResource(R.string.fetch_error)))
                                errorChannel.send(
                                    responseResource.text ?: TextWrapper
                                        .StringResource(R.string.unknown_error),
                                ) // TODO(jherscu): log result from fragment
                                break@cachePipeline
                            }
                        }

                    // Cache mapped data or report mapping error and break loop
                    val cacheResource: SimpleResource =
                        when (mappingResource) {
                            is Resource.Success -> {
                                with(mappingResource.data!!) {
                                    weatherUseCases.cacheWeatherUseCase(first, second)
                                }
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(UiEvent.ShowSnackbar(TextWrapper.StringResource(R.string.map_error)))
                                errorChannel.send(
                                    responseResource.text ?: TextWrapper
                                        .StringResource(R.string.unknown_error),
                                ) // TODO(jherscu): log result from fragment
                                break@cachePipeline
                            }
                        }

                    // End pipeline with either success or error reporting
                    when (cacheResource) {
                        is Resource.Success -> {
                            Timber.i("Weather cached successfully!")
                            _weatherIsRecent.value = true
                            _weatherInCache.value = true
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(TextWrapper.StringResource(R.string.cache_error)))
                            errorChannel.send(
                                responseResource.text ?: TextWrapper
                                    .StringResource(R.string.unknown_error),
                            ) // TODO(jherscu): log result from fragment
                            break@cachePipeline
                        }
                    }
                }

                val upToDate =
                    weatherUseCases.getWeatherDataUseCase(
                        daily = true,
                        qualifiedName = city,
                        weatherInCache = weatherInCache.value!!,
                        weatherIsRecent = weatherIsRecent.value!!,
                    )

                upToDate.collect { forecastListRes ->
                    when (forecastListRes) {
                        is Resource.Success -> {
                            forecastListRes.text?.let { // If data is old a snackbar will alert the user
                                _eventFlow.emit(UiEvent.ShowSnackbar(it))
                            }
                            // TODO(jherscu): Set Data to holder variable
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackbar(TextWrapper.StringResource(R.string.cache_error)))
                            this.cancel() // Cancels viewModelScope
                        }
                    }
                }
            }
        }
    }
