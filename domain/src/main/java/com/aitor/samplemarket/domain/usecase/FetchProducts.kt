package com.aitor.samplemarket.domain.usecase

import arrow.core.Either
import com.aitor.samplemarket.domain.model.ApiError
import com.aitor.samplemarket.domain.model.Product

interface FetchProducts {
    operator fun invoke(filterCriteria: String? = null): Either<ApiError, List<Product>>
}

