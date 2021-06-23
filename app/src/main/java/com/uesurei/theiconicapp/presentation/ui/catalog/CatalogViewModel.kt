package com.uesurei.theiconicapp.presentation.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uesurei.theiconicapp.network.data.TheIconicRepository
import com.uesurei.theiconicapp.network.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel
@Inject
constructor(
    private val repository: TheIconicRepository
) : ViewModel() {

    fun fetchCatalog(): Flow<PagingData<Product>> {
        return repository.getProductRepoResults().cachedIn(viewModelScope)
    }
}