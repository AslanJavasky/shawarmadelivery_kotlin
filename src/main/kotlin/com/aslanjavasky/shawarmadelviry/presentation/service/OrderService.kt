package com.aslanjavasky.shawarmadelviry.presentation.service

import com.aslanjavasky.shawarmadelviry.domain.interractor.OrderInterractor
import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderService(
    @Qualifier("OrderRepoAdapter_CRUD") private val orderRepo: OrderRepo
):OrderInterractor(orderRepo)