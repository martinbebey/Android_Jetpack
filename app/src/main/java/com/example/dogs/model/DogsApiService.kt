package com.example.dogs.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {
    private val BASE_URL = "https://raw.githubusercontent.com/DevTides/" //base url for the api json
    private val api = Retrofit.Builder()//builds a retrofit object
        .baseUrl(BASE_URL)//give it a base
        .addConverterFactory(GsonConverterFactory.create())//convert the json into the list of vars in our model
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//turn the whole thing into and observable such as Single
        .build()//build it
        .create(DogsAPI::class.java)//based on our API interface and add all the methods of the interface below

    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }
}