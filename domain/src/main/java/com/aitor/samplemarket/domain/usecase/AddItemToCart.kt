package com.aitor.samplemarket.domain.usecase

import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.domain.repository.CartRepository
import javax.inject.Inject

class AddItemToCart @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(product: Product, amount: Int) {
        cartRepository.addItemToCart(product, amount)
    }
}