package com.aitor.samplemarket.shop.screens.shop.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.common.toInt
import com.aitor.samplemarket.databinding.LayoutProductBinding
import com.aitor.samplemarket.domain.model.Product
import timber.log.Timber

class ProductAdapter(private val productClickListener: (Product, Int) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: LayoutProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                productName.text = product.name
                productPrice.text = root.context.getPriceString(product.price)
                if (product.hasDiscount) {
                    Timber.d("setupDiscountedView")
                    setupDiscountedView(product)
                } else {
                    Timber.d("setupNormalView")
                    setupNormalView()
                }

                setupBuyButton(product)
                setupBuyAmountBox(product)
            }
        }

        private fun setupDiscountedView(product: Product) {
            with(binding) {
                if (product.discountedPrice != null) {
                    productFinalPrice.visibility = View.VISIBLE
                    productFinalPrice.text = root.context.getPriceString(product.discountedPrice!!)
                    productPrice.paintFlags = STRIKE_THRU_TEXT_FLAG
                }

                productName.setTextColor(root.context.getColor(R.color.colorAccent))
            }
        }

        private fun setupNormalView() {
            with(binding) {
                productFinalPrice.visibility = View.GONE
                productPrice.paintFlags = productPrice.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                productName.setTextColor(root.context.getColor(R.color.softBlack))
            }
        }

        private fun setupBuyAmountBox(product: Product) {
            with(binding) {
                buyAmount.apply {
                    setOnEditorActionListener { _, actionId, _ ->
                        val amountInt = buyAmount.toInt()
                        if (amountInt > 0 && actionId == EditorInfo.IME_ACTION_DONE) {
                            submitAmount(product, amountInt)
                        }
                        false
                    }
                    addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable) {}

                        override fun beforeTextChanged(
                            s: CharSequence, start: Int, count: Int, after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence, start: Int, before: Int, count: Int
                        ) {
                            buyButton.visibility = if (s == "") {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                        }
                    })
                    setOnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus && buyAmount.toInt() == 0) {
                            buyButton.visibility = View.GONE
                        }
                    }
                }
            }
        }

        private fun setupBuyButton(product: Product) {
            with(binding) {
                buyButton.setOnClickListener {
                    val amountInt = buyAmount.toInt()
                    if (amountInt > 0) {
                        submitAmount(product, amountInt)
                    }
                }
            }
        }

        private fun submitAmount(product: Product, amountInt: Int) {
            productClickListener(product, amountInt)
            binding.buyAmount.text.clear()
            binding.buyButton.visibility = View.GONE
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}