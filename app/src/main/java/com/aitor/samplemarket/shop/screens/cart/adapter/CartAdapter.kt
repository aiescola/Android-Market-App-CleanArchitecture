package com.aitor.samplemarket.shop.screens.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.common.toInt
import com.aitor.samplemarket.databinding.LayoutCartItemBinding
import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.model.Product

class CartAdapter(
    private val deleteClickListener: (Product) -> Unit,
    val amountUpdatedListener: (Product, Int) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartItemViewHolder>(CartItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartItemViewHolder(private val binding: LayoutCartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            with(binding) {
                itemName.text = cartItem.product.name

                setupPriceLabels(cartItem.nonDiscountedSubtotal, cartItem.discountedSubtotal)
                setupAmountLabel(cartItem.amount, cartItem.product)

                deleteButton.setOnClickListener {
                    deleteClickListener(cartItem.product)
                }
            }
        }

        private fun setupAmountLabel(amountInt: Int, product: Product) {
            with(binding) {
                amount.setText(amountInt.toString(), TextView.BufferType.EDITABLE)
                amount.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        amountUpdatedListener(product, amount.toInt())
                    }
                    false
                }
            }
        }

        private fun setupPriceLabels(nonDiscountedSubtotal: Double, subtotal: Double?) {
            with(binding) {
                itemPrice.text = root.context.getPriceString(nonDiscountedSubtotal)

                if (subtotal == null) {
                    binding.itemFinalPrice.visibility = View.GONE
                } else {
                    itemFinalPrice.text = root.context.getPriceString(subtotal)
                    itemFinalPrice.visibility = View.VISIBLE
                }
            }
        }
    }
}

class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.product == newItem.product && oldItem.amount == newItem.amount
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}