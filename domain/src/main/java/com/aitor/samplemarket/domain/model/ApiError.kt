package com.aitor.samplemarket.domain.model

sealed class ApiError {
    data class UnknownError(val code: Int) : ApiError()
    object NotFoundError : ApiError()
    object NetworkError : ApiError()
}
