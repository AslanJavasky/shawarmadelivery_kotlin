package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.DeliveryEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryCassandraRepository : CassandraRepository<DeliveryEntity, UUID> {
}