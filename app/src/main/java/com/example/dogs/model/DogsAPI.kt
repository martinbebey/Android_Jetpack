package com.example.dogs.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

interface DogsAPI {
    //can also have POST
    @GET("DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}