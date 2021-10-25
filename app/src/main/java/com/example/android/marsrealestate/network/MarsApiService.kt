/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

//Similar with what we did with Retrofit, we need to create a Moshi object
//using the Moshi builder
//In order for Moshi's annotations to work properly with Kotlin
//we need to add the Kotlin JSON adapter factory
private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//Retrofit will create an object that implements our interface
//with all of the methods that talk to the server
interface MarsApiService{
    //here we specify the endpoint
    @GET("realestate")
    fun getProperties(): Call<List<MarsProperty>>
    //the call object is used to start the request. To create a retrofit service you call
    //retrofit.create, parsing in the service interface API we just defined.
    //Since the retrofit create calls is expensive, and our app only needs one retrofit service instance
    //we'll expose our retrofit service to the rest of the application
    //using a public object called MarsAPI
}
//In this object we'll dd a lazily initialized retrofit object
//properly named retrofitService, which gets initialized using the retrofit.create method
//with our MarsApiService interface.
object MarsApi{
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
    //Calling MarsApi.retrofitService will return a retrofit object that implements MarsApiService
}
