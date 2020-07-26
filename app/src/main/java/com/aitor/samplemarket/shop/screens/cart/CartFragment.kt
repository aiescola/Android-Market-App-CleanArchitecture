package com.aitor.samplemarket.shop.screens.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.shop.screens.cart.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This screen displays the cart view with each product and the final price.
 * The user can update the amount of items of a certain product or delete it from the cart.
 */
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModel()

    private lateinit var cartAdapter: CartAdapter

    private val cartObserver = Observer<List<CartItem>> {
        setTotalPriceLabel(it)
        cartAdapter.submitList(it)
    }

    private val cartTextChangedListener: (Product, Int) -> Unit = { product, amount ->
        if (amount > 0) {
            cartViewModel.updateItem(product, amount)
        } else {
            cartViewModel.removeItem(product)
        }
    }

    private val cartDeleteItemClickListener: (Product) -> Unit = {
        cartViewModel.removeItem(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(cartDeleteItemClickListener, cartTextChangedListener)
        cartViewModel.cartItems.observe(this, cartObserver)

        setupCartRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartViewModel.cartItems.removeObserver(cartObserver)
    }

    private fun setupCartRecyclerView() {
        cart_recycler_view.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }
    }

    private fun setTotalPriceLabel(it: List<CartItem>) {
        val totalPrice =
            it.map { item -> item.discountedSubtotal ?: item.nonDiscountedSubtotal }.sum()
        cart_total_price.text = context?.getPriceString(totalPrice)
    }
}
