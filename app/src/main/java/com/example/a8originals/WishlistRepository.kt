package com.example.a8originals

// WishlistRepository.kt
import com.example.a8originals.data.WishlistItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class WishlistRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getWishlist(onResult: (List<WishlistItem>?) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId).collection("wishlist")
            .get()
            .addOnSuccessListener { documents ->
                val wishlist = documents.mapNotNull { it.toObject(WishlistItem::class.java) }
                onResult(wishlist)
            }
            .addOnFailureListener { e ->
                println("Error retrieving wishlist: $e")
                onResult(null)
            }
    }

    fun addItemToWishlist(item: WishlistItem): Task<Void> {
        val userId = auth.currentUser?.uid ?: return Tasks.forException<Void>(Exception("User ID not available"))
        return db.collection("users").document(userId).collection("wishlist")
            .document(item.itemId)
            .set(item)
    }

    fun removeItemFromWishlist(itemId: String): Task<Void> {
        val userId = auth.currentUser?.uid ?: return Tasks.forException<Void>(Exception("User ID not available"))
        return db.collection("users").document(userId).collection("wishlist")
            .document(itemId)
            .delete()
    }
}
