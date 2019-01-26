package com.github.lucasfsousa.pricetag

import java.math.BigDecimal

data class Product(
    var store: String,
    var countryCode: String,
    var brand: String? = null,
    var url: String,
    var title: String,
    var priceAsText: String,
    var price: BigDecimal,
    var images: List<String> = emptyList(),
    var metadata: Map<String, String> = emptyMap()
)