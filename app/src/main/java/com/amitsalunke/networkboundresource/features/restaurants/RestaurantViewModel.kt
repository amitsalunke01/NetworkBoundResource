package com.amitsalunke.networkboundresource.features.restaurants

import androidx.lifecycle.*
import com.amitsalunke.networkboundresource.api.RestaurantApi
import com.amitsalunke.networkboundresource.data.Restaurant
import com.amitsalunke.networkboundresource.data.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //@ViewModelInject has been depreceated so use
class RestaurantViewModel @Inject constructor(
    repository: RestaurantRepository
) : ViewModel() {

    /*//following liveData is private because activity should not be able to modify it only viewModel can change it
    private val restaurantLiveData = MutableLiveData<List<Restaurant>>()

    val restaurants: LiveData<List<Restaurant>> = restaurantLiveData

    //as the viewModel gets or creates instance following init block will get executed
    init {
        viewModelScope.launch {
            val restaurants = api.getRestaurant()
            delay(2000)
            restaurantLiveData.value = restaurants
        }
    }*/
    //following is executed in an coroutine
    val restaurants = repository.getRestaurants().asLiveData()
}