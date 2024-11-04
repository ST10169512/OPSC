package com.example.a8originals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.a8originals.data.Product

@Composable
fun MainScreen(products1: List<Product>) {
    val navController = rememberNavController()
    // Define wishlist items as a mutable list
    val wishlistItems = remember { mutableStateListOf<Product>() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            wishlistItems = wishlistItems // Pass wishlistItems here
        )
    }
}


@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    wishlistItems: MutableList<Product> // Accept wishlist items as a parameter
) {
    NavHost(navController = navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen(wishlistItems) } // Pass wishlistItems to HomeScreen
        composable("Wishlist") { WishlistScreen(wishlistItems) } // Pass to WishlistScreen
        composable("Settings") { SettingsScreen() }
    }
}


// Updated Home Screen to Display Product Grid
@Composable
fun HomeScreen(wishlistItems: List<Product>) {
    // Sample product data (in practice, this might come from a ViewModel or backend)
    val products = remember {
        mutableStateListOf(
            Product("T-Shirt", "https://nolson.nl/cdn/shop/products/mens_tshirt_grey_2048x.jpg?v=1709645461", 19.99),
            Product("Jacket", "https://pngimg.com/uploads/jacket/jacket_PNG8028.png", 49.99),
            Product("Shoes", "https://peopleoftheseine.com/wp-content/uploads/2020/04/F-Scott-Derby-Blue-Suede-1-1.jpg ", 89.99)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(product, wishlistItems as MutableList<Product>) // Pass wishlistItems to ProductItem
        }
    }
}

@Composable
fun ProductItem(product: Product, wishlistItems: MutableList<Product>) {

    val isInWishlist = product in wishlistItems

    IconButton(onClick = {
        if (isInWishlist) wishlistItems.remove(product) else wishlistItems.add(product)
    }) {
        Icon(
            imageVector = if (isInWishlist) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Add to Wishlist"
        )
    }
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Button to add/remove from wishlist
            Button(
                onClick = {
                    if (wishlistItems.contains(product)) {
                        wishlistItems.remove(product)
                    } else {
                        wishlistItems.add(product)
                    }
                }
            ) {
                Text(if (wishlistItems.contains(product)) "Remove from Wishlist" else "Add to Wishlist")
            }
        }
    }
}


// Wishlist Screen to display wishlist items
@Composable
fun WishlistScreen(wishlistItems: List<Product>) {
    if (wishlistItems.isEmpty()) {
        Text(
            text = "Your wishlist is empty!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(wishlistItems) { product ->
                ProductItem(product, wishlistItems as MutableList<Product>)
            }
        }
    }
}

// Settings Screen Placeholder
@Composable
fun SettingsScreen() {
    Text(text = "Settings Screen")
}

// Bottom Navigation Bar
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("Home", "Wishlist", "Settings")
    val icons = listOf(Icons.Default.Home, Icons.Default.Favorite, Icons.Default.Settings)

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = icons[index], contentDescription = screen)
                },
                label = { Text(screen) },
                selected = currentRoute == screen,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


