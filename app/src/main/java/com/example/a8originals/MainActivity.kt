package com.example.a8originals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.example.a8originals.ui.theme._8OriginalsTheme

// Main Activity
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ProductViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setContent {
            _8OriginalsTheme {
                // Use the ViewModel's StateFlow to observe product list changes
                val products = viewModel.products.collectAsState().value // No need for initial emptyList() here

                LaunchedEffect(Unit) {
                    viewModel.getProducts() // Fetch products when the composable is first launched
                }

                MainScreen(products) // Pass the products to your MainScreen
            }
        }
    }
}

