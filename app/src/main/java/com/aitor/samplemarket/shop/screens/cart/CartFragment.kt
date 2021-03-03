package com.aitor.samplemarket.shop.screens.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.common.getPriceString
import com.aitor.samplemarket.databinding.FragmentCartBinding
import com.aitor.samplemarket.domain.model.CartItem
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.shop.screens.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * This screen displays the cart view with each product and the final price.
 * The user can update the amount of items of a certain product or delete it from the cart.
 */
@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var binding: FragmentCartBinding
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
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(cartDeleteItemClickListener, cartTextChangedListener)
        cartViewModel.cartItems.observe(viewLifecycleOwner, cartObserver)

        setupCartRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartViewModel.cartItems.removeObserver(cartObserver)
    }

    private fun setupCartRecyclerView() {
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }
    }

    private fun setTotalPriceLabel(it: List<CartItem>) {
        val totalPrice =
            it.map { item -> item.discountedSubtotal ?: item.nonDiscountedSubtotal }.sum()
        binding.cartTotalPrice.text = context?.getPriceString(totalPrice)
    }
}
