package com.example.etherealartefacts.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etherealartefacts.models.request.Category
import com.example.etherealartefacts.models.request.Product
import com.example.etherealartefacts.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val products = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    private val filteredProducts = _filteredProducts

    private val _isTriggered = MutableStateFlow(false)
    private val isTriggered = _isTriggered.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _filterCriteria = MutableStateFlow("")
    val filterCriteria = _filterCriteria.asStateFlow()

    private val _filtersNumber = MutableStateFlow(0)
    val filtersNumber = _filtersNumber.asStateFlow()

    private val _minPrice = MutableStateFlow(0f)
    val minPrice = _minPrice.asStateFlow()

    private val _maxPrice = MutableStateFlow(0f)
    val maxPrice = _maxPrice.asStateFlow()

    private val _selectedStars = MutableStateFlow(0)
    val selectedStars = _selectedStars.asStateFlow()

    private val _selectedCategories = MutableStateFlow<List<Category>>(emptyList())
    val selectedCategories = _selectedCategories.asStateFlow()

    private val _updatedRange = MutableStateFlow(minPrice.value..maxPrice.value)
    var updatedRange = _updatedRange.asStateFlow()

    private var lastMinPrice: Float = 0f
    private var lastMaxPrice: Float = 0f
    private var lastSelectedCategories: List<Category> = emptyList()
    private var lastSelectedStars: Int = 0

    val displayedProducts: StateFlow<List<Product>> = combine(
        filterCriteria,
        products,
        filteredProducts,
        isTriggered,
    ) { criteria, allProducts, filtered, isTriggered ->
        if (criteria.isNotEmpty() || isTriggered) {
            filtered
        } else {
            allProducts
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun onChange(value: String) {
        _filterCriteria.value = value
        val filteredList = products.value.filter { product: Product ->
            product.title.contains(value, ignoreCase = true)
        }
        _filteredProducts.value = filteredList
    }

    fun onFiltersApplied(
        selectedStars: Int, minPrice: Int, maxPrice: Int, categories: List<Category>
    ) {
        if (selectedStars == lastSelectedStars && minPrice.toFloat() == lastMinPrice && maxPrice.toFloat() == lastMaxPrice && categories == lastSelectedCategories) {
            return
        }

        _selectedStars.value = selectedStars
        _minPrice.value = minPrice.toFloat()
        _maxPrice.value = maxPrice.toFloat()
        _selectedCategories.value = categories

        _isTriggered.value = true

        val filteredList = if (selectedStars == 0) {
            products.value.filter { product ->
                product.price > minPrice && product.price < maxPrice && categories.any { category -> product.category == category.name }
            }
        } else {
            products.value.filter { product ->
                product.rating == selectedStars && product.price > minPrice && product.price < maxPrice && categories.any { category -> product.category == category.name }
            }
        }

        if (selectedStars != lastSelectedStars) {
            _filtersNumber.value++
        }

        if (minPrice.toFloat() != lastMinPrice || maxPrice.toFloat() != lastMaxPrice) {
            _filtersNumber.value++
        }

        if (categories != lastSelectedCategories) {
            _filtersNumber.value++
        }

        _filteredProducts.value = filteredList

        lastSelectedStars = selectedStars
        lastMinPrice = minPrice.toFloat()
        lastMaxPrice = maxPrice.toFloat()
        lastSelectedCategories = categories

    }

    fun resetFiltersNumber() {
        _filtersNumber.value = 0
    }

    fun onRangeChanged(newRange: ClosedFloatingPointRange<Float>) {
        _updatedRange.value = newRange
    }

    fun onUpdateStarsNumber(number: Int) {
        _selectedStars.value = if (number == selectedStars.value) 0 else number
    }

    fun updateCategory(categoryId: Int, isChecked: Boolean) {
        val updatedCategories = categories.value.map { category ->
            if (category.id == categoryId) {
                category.copy(isChecked = isChecked)
            } else {
                category
            }
        }
        _categories.value = updatedCategories
    }

    fun clear() {
        _filterCriteria.value = ""
    }


    private fun getAllProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response: Response<List<Product>> = repository.getAllProductJsonResponse()

                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()
                    _products.value = products!!
                } else {
                    _isError.value = true
                }
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val response: Response<List<Category>> = repository.getAllCategories()
                if (response.isSuccessful && response.body() != null) {
                    val categories = response.body()?.map { category ->
                        category.isChecked = true
                        category
                    }
                    _categories.value = categories.orEmpty()
                } else {
                    _isError.value = true
                }
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    init {
        getAllProducts()

        viewModelScope.launch {
            products.collect { allProducts ->
                val minPriceValue = allProducts.minOfOrNull { it.price }
                val maxPriceValue = allProducts.maxOfOrNull { it.price }

                _minPrice.value = minPriceValue?.toFloat() ?: 0f
                _maxPrice.value = maxPriceValue?.toFloat() ?: 100f
                _updatedRange.value = (_minPrice.value.._maxPrice.value)
            }
        }

        getAllCategories()
    }
}
