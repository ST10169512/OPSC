package com.example.a8originals

// UserProfileRepository.kt
import com.example.a8originals.data.UserProfile
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.tasks.TaskCompletionSource

class UserProfileRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getUserProfile(onResult: (UserProfile?) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                val profile = document.toObject(UserProfile::class.java)
                onResult(profile)
            }
            .addOnFailureListener { e ->
                println("Error retrieving user profile: $e")
                onResult(null)
            }
    }

    fun saveUserProfile(userProfile: UserProfile): Task<Void> {
        val userId = auth.currentUser?.uid ?: return Tasks.forException<Void>(Exception("User ID not available"))

        return db.collection("users").document(userId)
            .set(userProfile)
    }
}
