package com.aitor.samplemarket.domain.repository

import com.aitor.samplemarket.domain.model.User

interface UserRepository {

    fun getUser(): User

}