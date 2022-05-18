package com.jHerscu.clearskies.utils.mappers

import com.jHerscu.clearskies.data.model.GeocodedCity
import com.jHerscu.clearskies.data.model.response.CityResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity

class GeocodedCityMapper {

    fun remoteToLocal(remoteData: CityResponse): LocalGeocodedCity {
        remoteData.let {
            return LocalGeocodedCity(
                latitude = it.lat,
                longitude = it.lon,
                qualifiedName = "${it.city}, ${it.stateCode}"
            )
        }
    }

    fun localToData(localData: LocalGeocodedCity): GeocodedCity {
        localData.let {
            return GeocodedCity(
                latitude = it.latitude,
                longitude = it.longitude,
                qualifiedName = it.qualifiedName
            )
        }
    }

}