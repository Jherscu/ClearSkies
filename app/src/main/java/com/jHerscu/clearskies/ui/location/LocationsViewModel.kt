package com.jHerscu.clearskies.ui.location

import androidx.lifecycle.ViewModel
import com.jHerscu.clearskies.domain.location.CitiesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel
    @Inject
    constructor(
        private val citiesUseCases: CitiesUseCases,
    ) : ViewModel()
