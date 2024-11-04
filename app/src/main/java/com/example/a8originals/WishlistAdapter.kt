package com.example.a8originals

// WishlistAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a8originals.data.WishlistItem

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private val wishlistItems = mutableListOf<WishlistItem>()

    fun setWishlist(items: List<WishlistItem>) {
        wishlistItems.clear()
        wishlistItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(wishlistItems[position])
    }

    override fun getItemCount(): Int = wishlistItems.size

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)
        private val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)

        fun bind(item: WishlistItem) {
            itemName.text = item.name
            itemPrice.text = "$${item.price}"
            Glide.with(itemView.context).load(item.imageUrl).into(itemImage)
        }
    }
}
