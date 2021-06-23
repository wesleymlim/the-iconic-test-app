package com.uesurei.theiconicapp.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uesurei.theiconicapp.presentation.components.DetailView
import com.uesurei.theiconicapp.presentation.components.NavigationTopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var productSKU: MutableState<String?> = mutableStateOf(null)
    private var productName: MutableState<String?> = mutableStateOf(null)
    private var productPrice: MutableState<Double?> = mutableStateOf(null)
    private var productFinalPrice: MutableState<Double?> = mutableStateOf(null)
    private var productDesc: MutableState<String?> = mutableStateOf(null)
    private var productBrand: MutableState<String?> = mutableStateOf(null)
    private var productImage: MutableState<String?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("productSKU")?.let { sku ->
            productSKU.value = sku
        }
        arguments?.getString("productName")?.let { name ->
            productName.value = name
        }
        arguments?.getDouble("productPrice")?.let { price ->
            productPrice.value = price
        }
        arguments?.getDouble("productFinalPrice")?.let { finalPrice ->
            productFinalPrice.value = finalPrice
        }
        arguments?.getString("productDesc")?.let { desc ->
            productDesc.value = desc
        }
        arguments?.getString("productBrand")?.let { brand ->
            productBrand.value = brand
        }
        arguments?.getString("productImage")?.let { image ->
            productImage.value = image
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(topBar = {
                    NavigationTopBar(
                        if (productBrand.value != null) {
                            "${productBrand.value}"
                        } else {
                            "Loading..."
                        },
                        findNavController()
                    )
                }) {
                    DetailView(
                        sku = if (productSKU.value != null) {
                            "${productSKU.value}"
                        } else {
                            "Loading..."
                        },
                        price = if (productPrice.value != null) {
                            productPrice.value!!
                        } else {
                            0.0
                        },
                        final_price = if (productFinalPrice.value != null) {
                            productFinalPrice.value
                        } else {
                            0.0
                        },
                        description = if (productDesc.value != null) {
                            "${productDesc.value}"
                        } else {
                            "Loading..."
                        },
                        name = if (productName.value != null) {
                            "${productName.value}"
                        } else {
                            "Loading..."
                        },
                        image = if (productImage.value != null) {
                            "${productImage.value}"
                        } else {
                            "Loading..."
                        }
                    )
                }
            }
        }
    }
}