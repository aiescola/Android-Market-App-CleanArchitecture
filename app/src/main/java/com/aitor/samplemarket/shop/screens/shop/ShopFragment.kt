package com.aitor.samplemarket.shop.screens.shop

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.base.features.SearchFilterFeature
import com.aitor.samplemarket.common.showToast
import com.aitor.samplemarket.databinding.FragmentShopBinding
import com.aitor.samplemarket.databinding.LayoutErrorBinding
import com.aitor.samplemarket.domain.model.Discount
import com.aitor.samplemarket.domain.model.Product
import com.aitor.samplemarket.shop.screens.shop.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * This screen displays the product list and allows the user to buy
 * the desired amount of each. It also features a filter box for easier
 * navigation with a big list.
 */
@AndroidEntryPoint
class ShopFragment : Fragment() {

    private val shopViewModel: ShopViewModel by viewModels()
    @Inject lateinit var searchFilterFeature: SearchFilterFeature

    private lateinit var binding: FragmentShopBinding
    private lateinit var errorLayoutBinding: LayoutErrorBinding

    private var isStatusLoading = false
    private lateinit var productAdapter: ProductAdapter

    private val shopStatusObserver = Observer<ShopViewModel.ProductStatus> {
        isStatusLoading = false
        when (it) {
            is ShopStatusLoading -> onLoading()
            is ShopStatusLoaded -> onProductsLoaded(it.products)
            is ShopStatusFailure -> showErrorScreen()
        }
    }

    private val discountObserver = Observer<Discount?> { discount ->
        if (discount != null) {
            showDiscountsLayout(discount)
        } else {
            hideDiscountsLayout()
        }
    }

    private val productBuyClickListener: (Product, Int) -> Unit = { product, amount ->
        shopViewModel.addToCart(product, amount)
        context?.showToast("$amount ${product.name} added to cart")
        Timber.d("Add to cart, product: $product Amount: $amount")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        errorLayoutBinding = binding.layoutError
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")

        productAdapter = ProductAdapter(productBuyClickListener)

        shopViewModel.productStatus.observe(viewLifecycleOwner, shopStatusObserver)
        shopViewModel.discount.observe(viewLifecycleOwner, discountObserver)

        setupProductsRecyclerView()
        setupSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
        shopViewModel.productStatus.removeObserver(shopStatusObserver)
        shopViewModel.discount.removeObserver(discountObserver)
    }

    private fun setupProductsRecyclerView() {
        binding.productContainer.apply {
            setHasFixedSize(true)
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }
    }

    private fun setupSearchBar() {
        if (searchFilterFeature.enabled) {
            binding.searchFilter.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    shopViewModel.applySearchFilter(s.toString())
                }
            })
        } else {
            binding.searchFilter.visibility = View.GONE
        }
    }

    private fun onLoading() {
        Timber.d("Status loading")

        with(binding) {
            errorLayoutBinding.errorText.visibility = View.GONE
            errorLayoutBinding.errorRefresh.visibility = View.GONE
            productContainer.visibility = View.GONE
            searchFilter.visibility = View.GONE

            progressBar.visibility = View.VISIBLE
        }
    }

    private fun onProductsLoaded(products: List<Product>) {
        Timber.d("Page loaded. Products: $products")

        with(binding) {
            errorLayoutBinding.errorText.visibility = View.GONE
            errorLayoutBinding.errorRefresh.visibility = View.GONE
            progressBar.visibility = View.GONE

            productContainer.visibility = View.VISIBLE
            if (searchFilterFeature.enabled) {
                searchFilter.visibility = View.VISIBLE
            }
        }

        productAdapter.submitList(products)
    }

    private fun showErrorScreen() {
        val errorMsg = getString(R.string.error_loading_products)
        Timber.d("Error. message: $errorMsg")
        with(binding) {
            errorLayoutBinding.errorText.apply {
                text = errorMsg
                visibility = View.VISIBLE
            }
            errorLayoutBinding.errorRefresh.apply {
                setOnClickListener { shopViewModel.loadData() }
                visibility = View.VISIBLE
            }

            progressBar.visibility = View.GONE
            searchFilter.visibility = View.GONE
            layoutDiscount.layoutDiscount.visibility = View.GONE
            productContainer.visibility = View.GONE
        }
    }

    private fun showDiscountsLayout(discount: Discount) {
        Timber.d("Display discounts layout: $discount")
        val s = "${discount.name} ${discount.description}".toSpannable()
        s[0, discount.name.length] = StyleSpan(Typeface.BOLD_ITALIC)

        binding.layoutDiscount.apply {
            discountDescription.text = s
            layoutDiscount.visibility = View.VISIBLE
        }
    }

    private fun hideDiscountsLayout() {
        Timber.d("Hide discount layout")
        binding.layoutDiscount.layoutDiscount.visibility = View.GONE
    }
}
