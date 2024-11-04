package com.example.a8originals

// WishlistActivity.kt
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a8originals.data.WishlistItem

class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemButton: Button
    private val wishlistRepository = WishlistRepository()
    private val wishlistAdapter = WishlistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        recyclerView = findViewById(R.id.recyclerView)
        addItemButton = findViewById(R.id.addItemButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wishlistAdapter

        loadWishlist()

        addItemButton.setOnClickListener {
            val item = WishlistItem(itemId = "sampleItemId", name = "Sample Item", price = 29.99)
            addItemToWishlist(item)
        }
    }

    private fun loadWishlist() {
        wishlistRepository.getWishlist { items ->
            if (items != null) {
                wishlistAdapter.setWishlist(items)
            } else {
                Toast.makeText(this, "Failed to load wishlist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addItemToWishlist(item: WishlistItem) {
        wishlistRepository.addItemToWishlist(item)
            .addOnSuccessListener {
                Toast.makeText(this, "Item added to wishlist", Toast.LENGTH_SHORT).show()
                loadWishlist()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
    }
}
