package com.example.a8originals

import com.example.a8originals.data.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Define a GET request to fetch a list of products
    @GET("products")
    suspend fun getProducts(): List<Product>

    // Define a GET request to fetch details of a single product by ID
    @GET("products/{id}")
    fun getProductDetails(@Path("id") productId: Int): Call<Product>
}
