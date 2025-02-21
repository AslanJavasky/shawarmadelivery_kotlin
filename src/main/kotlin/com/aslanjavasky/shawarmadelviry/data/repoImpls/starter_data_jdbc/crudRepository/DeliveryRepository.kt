package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("DeliveryRepoExtCrudRepo")
interface DeliveryRepository : CrudRepository<DeliveryEntity, Long>