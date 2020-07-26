package com.aitor.samplemarket.shop.screens.cart.adapter

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.common.inflate
import com.aitor.samplemarket.common.toInt
import com.aitor.samplemarket.domain.model.Product
import kotlinx.android.synthetic.main.layout_cart_item.view.*

class CartAdapter(
    private val deleteClickListener: (Product) -> Unit,
    val amountUpdatedListener: (Product, Int) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartItemViewHolder>(CartItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(parent.inflate(R.layout.layout_cart_item))
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(cartItem: CartItem) {
            with(view) {
                item_name.text = cartItem.product.name

                setupPriceLabels(cartItem.nonDiscountedSubtotal, cartItem.discountedSubtotal)
                setupAmountLabel(cartItem.amount, cartItem.product)

                delete_button.setOnClickListener {
                    deleteClickListener(cartItem.product)
                }
            }
        }

        private fun setupAmountLabel(amountInt: Int, product: Product) {
            with(view) {
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
            with(view) {
                item_price.text = context.getPriceString(nonDiscountedSubtotal)

                if (subtotal == null) {
                    view.item_final_price.visibility = View.GONE
                } else {
                    item_final_price.text = context.getPriceString(subtotal)
                    item_final_price.visibility = View.VISIBLE
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