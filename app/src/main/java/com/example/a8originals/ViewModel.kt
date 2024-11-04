package com.example.a8originals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a8originals.data.Product
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel : ViewModel() {
    // Mutable state flow to hold products
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    // Function to fetch products from API
    fun getProducts() {
        viewModelScope.launch {
            // Simulate a network call and update _products
            val productList = fetchProductsFromApi() // Replace with your API call
            _products.value = productList
        }
    }

    private suspend fun fetchProductsFromApi(): List<Product> {
        // Your API call logic here
        return listOf() // Return the fetched products
    }
}

