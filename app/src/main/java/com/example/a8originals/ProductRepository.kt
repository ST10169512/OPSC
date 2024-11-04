package com.example.a8originals

import com.example.a8originals.data.Product

class ProductRepository {
    suspend fun fetchProducts(): List<Product> {
        return RetrofitClient.apiService.getProducts()
    }
}