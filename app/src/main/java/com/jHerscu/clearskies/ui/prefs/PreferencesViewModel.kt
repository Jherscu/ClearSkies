package com.jHerscu.clearskies.ui.prefs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jHerscu.clearskies.data.repo.PreferencesRepoImpl
import com.jHerscu.clearskies.di.DefaultDispatcher
import com.jHerscu.clearskies.domain.repoInterface.PreferenceGroup
import com.jHerscu.clearskies.domain.repoInterface.PreferenceOrderComparator
import com.jHerscu.clearskies.domain.repoInterface.TranslatedPrefGroup
import com.jHerscu.clearskies.domain.repoInterface.UserPrefs
import com.jHerscu.clearskies.domain.useCase.preferences.ReadUserPrefsUseCase
import com.jHerscu.clearskies.utils.FLOW_SUBSCRIPTION_STOP_TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel
    @Inject
    constructor(
        // TODO(jherscu): Decide on repo impl pattern
        private val preferencesRepo: PreferencesRepoImpl,
        readUserPrefs: ReadUserPrefsUseCase,
        @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val prefState = readUserPrefs()
        private val prefGroups = MutableStateFlow(PreferenceGroup.defaultSortOrder)

        val viewState: StateFlow<ViewState> =
            prefGroups
                .combineTransform(prefState) { prefGroupOrder, userPrefs ->
                    emit(ViewState(userPrefs = userPrefs, prefGroups = prefGroupOrder))
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(FLOW_SUBSCRIPTION_STOP_TIMEOUT_MILLIS),
                    initialValue = ViewState(),
                )

        fun passIntent(intent: Intent) {
            when (intent) {
                is Intent.DynamicThemingModeClick -> {
                    setDynamicThemingMode(intent.pref)
                }
                is Intent.LockThemePrefClick -> {
                    setLockedThemePref(intent.prefName)
                }
                is Intent.TempUnitPrefClick -> {
                    setTempUnitPref(intent.prefName)
                }
                is Intent.HomeDisplayIntervalPrefClick -> {
                    setHomeDisplayIntervalPref(intent.prefName)
                }
                is Intent.TwentyFourHourModeClick -> {
                    set24HourMode(intent.pref)
                }
                is Intent.UpdateSortOrderClick -> {
                    setPrefOrderComparator(intent.sortMethod)
                }
            }
        }

        private fun setTempUnitPref(prefName: String) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.TEMP_UNIT_KEY, prefName)
            }

        private fun setHomeDisplayIntervalPref(prefName: String) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.HOME_DISPLAY_INTERVAL_KEY, prefName)
            }

        private fun set24HourMode(pref: Boolean) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.TWENTY_FOUR_HOUR_MODE_KEY, pref)
            }

        private fun setDynamicThemingMode(pref: Boolean) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.DYNAMIC_THEMING_KEY, pref)
            }

        private fun setLockedThemePref(prefName: String) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.LOCKED_THEME_KEY, prefName)
            }

        private fun setPrefOrderComparator(prefName: String) =
            viewModelScope.launch {
                preferencesRepo.writeToStore(PreferencesRepoImpl.PREF_ORDER_COMPARATOR_KEY, prefName)
            }

        suspend fun sortPrefGroups(
            translatedPrefGroups: List<TranslatedPrefGroup>,
            comparator: PreferenceOrderComparator,
        ): List<PreferenceGroup> {
            // Process on default disp. in case size of list grows as app expands
            return withContext(defaultDispatcher) {
                comparator.sortingFun(translatedPrefGroups)
            }
        }

        fun updatePrefGroupOrder(order: List<PreferenceGroup>) {
            prefGroups.value = order
        }

        data class ViewState(
            val userPrefs: UserPrefs = UserPrefs(),
            val prefGroups: List<PreferenceGroup> = PreferenceGroup.defaultSortOrder,
        )

        sealed interface Intent {
            data class HomeDisplayIntervalPrefClick(val prefName: String) : Intent

            data class TempUnitPrefClick(val prefName: String) : Intent

            data class LockThemePrefClick(val prefName: String) : Intent

            data class DynamicThemingModeClick(val pref: Boolean) : Intent

            data class TwentyFourHourModeClick(val pref: Boolean) : Intent

            data class UpdateSortOrderClick(val sortMethod: String) : Intent
        }
    }
