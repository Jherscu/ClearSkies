package com.jHerscu.clearskies.utils.mappers

import com.jHerscu.clearskies.data.model.GeocodedCity
import com.jHerscu.clearskies.data.model.response.CityResponse
import com.jHerscu.clearskies.data.source.local.entity.LocalGeocodedCity

class GeocodedCityMapper : Mapper.Geocode {

    override fun remoteToLocal(remoteData: CityResponse): LocalGeocodedCity {
        with(remoteData) {
            return LocalGeocodedCity(
                latitude = lat,
                longitude = lon,
                qualifiedName = "${city}, ${stateCode}"
            )
        }
    }

    override fun localToData(localData: LocalGeocodedCity): GeocodedCity {
        with(localData) {
            return GeocodedCity(
                latitude = latitude,
                longitude = longitude,
                qualifiedName = qualifiedName
            )
        }
    }

}