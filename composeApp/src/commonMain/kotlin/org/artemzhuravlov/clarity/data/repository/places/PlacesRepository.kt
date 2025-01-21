package org.artemzhuravlov.clarity.data.repository.places

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.data.model.place.PlaceDetails
import org.artemzhuravlov.clarity.data.model.place.PlaceDetailsResponse
import org.artemzhuravlov.clarity.data.model.place.PlacesResponse

class PlacesRepository(private val client: HttpClient, private val apiKey: String) :
    IPlacesRepository {

    private val places: MutableList<Place> = mutableListOf()

    override suspend fun fetchPlaces(
        location: String,
        radius: Int,
        type: String,
        onSuccess: ((List<Place>) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        if (places.isEmpty()) {
            try {
                val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                val response: HttpResponse = client.get(url) {
                    parameter("location", location)
                    parameter("radius", radius)
                    parameter("type", type)
                    parameter("key", apiKey)
                }
                val jsonResponse = response.bodyAsText()
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                val responseBody = json.decodeFromString(
                    PlacesResponse.serializer(), jsonResponse
                ).results
                places.addAll(responseBody.map {
                    Place(
                        name = it.name,
                        address = it.vicinity,
                        placeId = it.placeId,
                        latLng = it.geometry?.latLng ?: LatLng()
                    )
                })
                onSuccess?.invoke(places)
            } catch (e: Exception) {
                println("Exceptiion!!!! ${e.message}")
                onError?.invoke(e)
            }
        } else {
            onSuccess?.invoke(places)
        }
    }

    override suspend fun fetchPlaceDetails(
        placeId: String, onSuccess: ((PlaceDetails) -> Unit)?, onError: ((Exception) -> Unit)?
    ) {
        try {
            val url = "https://maps.googleapis.com/maps/api/place/details/json"
            val response: HttpResponse = client.get(url) {
                parameter("place_id", placeId)
                parameter("fields", "website,formatted_phone_number,opening_hours")
                parameter("key", apiKey)
            }
            val jsonResponse = response.bodyAsText()
            val json = Json {
                ignoreUnknownKeys = true
            }
            val result = json.decodeFromString(
                PlaceDetailsResponse.serializer(), jsonResponse
            ).result
            if (result == null) {
                onError?.invoke(Exception("Щось пішло не так, будь ласка спробуйте ще раз пізніше."))
            } else {
                onSuccess?.invoke(
                    result
                )
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}
