package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.DeliveryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryJpaRepository : JpaRepository<DeliveryEntity, Long>