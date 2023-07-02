package com.jHerscu.clearskies.data.repo

import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.data.model.response.SuggestedCitiesResponse
import com.jHerscu.clearskies.data.source.local.CityDao
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity
import com.jHerscu.clearskies.data.source.remote.GeocodeApiService
import com.jHerscu.clearskies.di.IoDispatcher
import com.jHerscu.clearskies.domain.repoInterface.GeocodeRepo
import com.jHerscu.clearskies.utils.Resource
import com.jHerscu.clearskies.utils.TextWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeocodeRepoImpl @Inject constructor(
    private val cityDao: CityDao,
    private val geocodeApiService: GeocodeApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GeocodeRepo {

    /**
     * Switches locally to IO thread pool and fetches the city list in a wrapper class
     * to track the success of the query.
     */
    override suspend fun fetchCityList(query: String): Resource<SuggestedCitiesResponse?> {
        return withContext(ioDispatcher) {
            try {
                val response = geocodeApiService.getListOfCities(query)
                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error(text = TextWrapper.DynamicString(response.message()))
                }
            } catch (e: IOException) {
                Resource.Error(text = TextWrapper.StringResource(R.string.bad_connection))
            } catch (e: HttpException) {
                Resource.Error(text = TextWrapper.StringResource(R.string.bad_response))
            }
        }
    }

    /*
        Database calls are executed from main thread as they offload to their own custom
        thread pool using the room/coroutine interoperability. Switching to Dispatchers.IO
        would otherwise slow down any operations.
    */

    override suspend fun getGeocodedCity(qualifiedName: String): LocalGeocodedCity {
        return cityDao.getCityByName(qualifiedName)
    }

    override suspend fun cacheGeocodedCity(city: LocalGeocodedCity) {
        cityDao.upsertCity(city)
    }

    override suspend fun deleteCity(city: LocalGeocodedCity) {
        cityDao.deleteCity(city)
    }

    /**
     * Subscribes downstream collectors to get the updated list of cities every time a new city
     * is added to the database.
     *
     * .distinctUntilChanged() filters out any redundant updates when
     * another part of the db changes.
     */
    override suspend fun getListOfGeocodedCities(): Flow<List<LocalGeocodedCity>> {
        return cityDao.getAllCities().distinctUntilChanged()
    }
}
