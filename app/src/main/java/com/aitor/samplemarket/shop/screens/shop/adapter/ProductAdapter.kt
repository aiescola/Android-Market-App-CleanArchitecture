package com.aitor.samplemarket.shop.screens.shop.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.common.inflate
import com.aitor.samplemarket.common.toInt
import com.aitor.samplemarket.domain.model.Product
import kotlinx.android.synthetic.main.layout_product.view.*
import timber.log.Timber

class ProductAdapter(private val productClickListener: (Product, Int) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflate(R.layout.layout_product))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            with(view) {
                product_name.text = product.name
                product_price.text = context.getPriceString(product.price)
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
            with(view) {
                if (product.discountedPrice != null) {
                    product_final_price.visibility = View.VISIBLE
                    product_final_price.text = context.getPriceString(product.discountedPrice!!)
                    product_price.paintFlags = STRIKE_THRU_TEXT_FLAG
                }

                product_name.setTextColor(context.getColor(R.color.colorAccent))
            }
        }

        private fun setupNormalView() {
            with(view) {
                product_final_price.visibility = View.GONE
                product_price.paintFlags = product_price.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                product_name.setTextColor(context.getColor(R.color.softBlack))
            }
        }

        private fun setupBuyAmountBox(product: Product) {
            view.buy_amount.apply {
                setOnEditorActionListener { _, actionId, _ ->
                    val amountInt = buy_amount.toInt()
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
                        view.buy_button.visibility = if (s == "") {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }
                })
                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && view.buy_amount.toInt() == 0) {
                        view.buy_button.visibility = View.GONE
                    }
                }
            }
        }

        private fun setupBuyButton(product: Product) {
            with(view) {
                buy_button.setOnClickListener {
                    val amountInt = buy_amount.toInt()
                    if (amountInt > 0) {
                        submitAmount(product, amountInt)
                    }
                }
            }
        }

        private fun submitAmount(product: Product, amountInt: Int) {
            productClickListener(product, amountInt)
            view.buy_amount.text.clear()
            view.buy_button.visibility = View.GONE
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}