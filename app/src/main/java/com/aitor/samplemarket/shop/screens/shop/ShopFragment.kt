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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aitor.samplemarket.R
import com.aitor.samplemarket.common.showToast
import com.aitor.samplemarket.discount.model.Discount
import com.aitor.samplemarket.product.model.Product
import com.aitor.samplemarket.shop.screens.shop.adapter.ProductAdapter
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.layout_discount.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * This screen displays the product list and allows the user to buy
 * the desired amount of each. It also features a filter box for easier
 * navigation with a big list.
 */
class ShopFragment : Fragment() {

    private val shopViewModel: ShopViewModel by viewModel()

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
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")

        productAdapter = ProductAdapter(productBuyClickListener)

        shopViewModel.productStatus.observe(this, shopStatusObserver)
        shopViewModel.discount.observe(this, discountObserver)

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
        product_container.apply {
            setHasFixedSize(true)
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }
    }

    private fun setupSearchBar() {
        search_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                shopViewModel.applySearchFilter(s.toString())
            }
        })
    }

    private fun onLoading() {
        Timber.d("Status loading")
        error_text.visibility = View.GONE
        error_refresh.visibility = View.GONE

        product_container.visibility = View.GONE
        search_filter.visibility = View.GONE

        progress_bar.visibility = View.VISIBLE
    }

    private fun onProductsLoaded(products: List<Product>) {
        Timber.d("Page loaded. Products: $products")
        error_text.visibility = View.GONE
        error_refresh.visibility = View.GONE
        progress_bar.visibility = View.GONE

        product_container.visibility = View.VISIBLE
        search_filter.visibility = View.VISIBLE

        productAdapter.submitList(products)
    }

    private fun showErrorScreen() {
        val errorMsg = getString(R.string.error_loading_products)
        Timber.d("Error. message: $errorMsg")
        error_text.apply {
            text = errorMsg
            visibility = View.VISIBLE
        }
        error_refresh.apply {
            setOnClickListener { shopViewModel.loadData() }
            visibility = View.VISIBLE
        }

        progress_bar.visibility = View.GONE
        search_filter.visibility = View.GONE
        layout_discount.visibility = View.GONE
        product_container.visibility = View.GONE
    }

    private fun showDiscountsLayout(discount: Discount) {
        Timber.d("Display discounts layout: $discount")
        val s = "${discount.name} ${discount.description}".toSpannable()
        s[0, discount.name.length] = StyleSpan(Typeface.BOLD_ITALIC)

        discount_description.text = s
        layout_discount.visibility = View.VISIBLE
    }

    private fun hideDiscountsLayout() {
        Timber.d("Hide discount layout")
        layout_discount.visibility = View.GONE
    }
}
