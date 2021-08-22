package com.amitsalunke.networkboundresource.data

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.amitsalunke.networkboundresource.api.RestaurantApi
import com.amitsalunke.networkboundresource.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: RestaurantApi,
    private val db: RestaurantDatabase
) {
    private val restaurantDao = db.restaurantDao()

    fun getRestaurants() = networkBoundResource(
        query = {
            restaurantDao.getAllRestaurant()
        },
        fetch = {
            delay(2000)
            api.getRestaurant()
        },
        saveFetchResult = { restaurants ->
            db.withTransaction {
                restaurantDao.deleteAllRestaurants()
                restaurantDao.insertRestaurants(restaurants)
            }

        }
    )
}