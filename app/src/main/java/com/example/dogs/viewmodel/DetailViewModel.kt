package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.DogBreed

class DetailViewModel: ViewModel() {
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(){
        val dog = DogBreed("1", "Corgi", "9 years", "breedGroup", "bredFor", "temperament", "")
        dogLiveData.value = dog
    }
}