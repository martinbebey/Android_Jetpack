package com.example.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao //data access object - interface to access objects from the room DB
interface DogDao {
    @Insert
    //suspend means run this on a different thread
    suspend  fun  insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogbreed") //in db everything is in lower case
    suspend  fun  getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend  fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend  fun deleteAllDogs()
}