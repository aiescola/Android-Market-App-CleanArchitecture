package com.aitor.samplemarket.data.repository

import com.aitor.samplemarket.domain.model.User
import com.aitor.samplemarket.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository {

    override fun getUser(): User = User("1234")

}
