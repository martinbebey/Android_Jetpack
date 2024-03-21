package com.example.dogs.model

import com.google.gson.annotations.SerializedName

data class DogBreed(
    @SerializedName("id")
    val breedId: String?,

    @SerializedName("name")
    val dogBreed: String?,

    @SerializedName("life_span")
    val lifespan: String?,

    @SerializedName("breed_group")
    val breedGroup: String?,

    // for retrofit to get data from json api for backend?
    //if var name matches name in JSON no need for annotation
    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("url")
    val imageUrl: String?
    )