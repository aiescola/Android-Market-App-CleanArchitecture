package com.aitor.samplemarket.shop.screens.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.usecase.DeleteCartItem
import com.aitor.samplemarket.domain.usecase.FetchCartItems
import com.aitor.samplemarket.domain.usecase.FetchDiscounts
import com.aitor.samplemarket.domain.usecase.UpdateCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val fetchDiscounts: FetchDiscounts,
    private val fetchCartItems: FetchCartItems,
    private val updateCartItem: UpdateCartItem,
    private val deleteCartItem: DeleteCartItem) : ViewModel() {

    private lateinit var discount: Either<ApiError, Discount>
    private val _cartItems: MutableLiveData<List<CartItem>> = MutableLiveData()
    val cartItems: LiveData<List<CartItem>>
        get() = _cartItems

    init {
        viewModelScope.launch {
            discount = fetchDiscounts()
            loadCartItems()
        }
    }

    fun updateItem(product: Product, amount: Int) {
        viewModelScope.launch {
            updateCartItem(product, amount)
            loadCartItems()
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            deleteCartItem(product)
            loadCartItems()
        }
    }

    private fun loadCartItems() {
        val cartItems = fetchCartItems()
        val cartItemsWithDiscounts = discount.fold(ifLeft = {
            cartItems
        }, ifRight = {discount ->
            cartItems.map {
                if (discount.codes.contains(it.product.code)) {
                    it.applyDiscount(discount)
                }
                it
            }
        })

        _cartItems.postValue(cartItemsWithDiscounts)
    }

    private fun CartItem.applyDiscount(discount: Discount) {
        discountedSubtotal = when (discount) {
            is Discount.ProductDiscount -> {
                discount.price * amount
            }
            is Discount.BulkDiscount -> {
                if (amount >= discount.minAmount) {
                    product.price * (1 - discount.pct / 100f) * amount
                } else null
            }
            is Discount.PromotionDiscount -> {
                if (amount > discount.buyRequiredAmount) {
                    val unitsToPay =
                        (amount / discount.buyRequiredAmount) * discount.pay + amount % discount.buyRequiredAmount
                    unitsToPay * product.price
                } else null
            }
        }
    }
}