package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.DogBreed

class DetailViewModel: ViewModel() {
    val dog = MutableLiveData<DogBreed>()

    fun refresh(){
        val dog1 = DogBreed("1", "Corgi", "9 years", "breedGroup", "bredFor", "temperament", "")
        dog.value = dog1
    }
}