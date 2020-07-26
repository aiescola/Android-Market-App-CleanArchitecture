package com.aitor.samplemarket.domain.model

interface DataSource<T> {
    val all: T
}