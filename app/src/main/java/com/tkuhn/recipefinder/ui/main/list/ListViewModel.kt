package com.tkuhn.recipefinder.ui.main.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.tkuhn.recipefinder.domain.Forecast
import com.tkuhn.recipefinder.repository.SpotifyRepo
import com.tkuhn.recipefinder.ui.BaseViewModel
import com.tkuhn.recipefinder.utils.LiveEvent

class ListViewModel(
    private val repo: SpotifyRepo
) : BaseViewModel() {

    val detailEvent = LiveEvent<Forecast>()
    val forecast = MutableLiveData<List<Forecast>>()
    val listItems = forecast.map {
        it.map { forecast ->
            ListItem(forecast.message) {
                goToDetail(forecast)
            }
        }
    }

    init {
        loadData()
        callApi()
    }

    fun loadData() {
        load(repo.loadForecast(), onData = {
            forecast.value = listOf(it)
        })
    }

    fun refreshData() {
        load(repo.refreshForecast(), onData = {
            forecast.value = listOf(it)
        })
    }

    fun callApi() {
        load(repo.callApi(), onSuccess = {
            //do something
        })
    }

    private fun goToDetail(forecast: Forecast) {
        detailEvent.value = forecast
    }
}