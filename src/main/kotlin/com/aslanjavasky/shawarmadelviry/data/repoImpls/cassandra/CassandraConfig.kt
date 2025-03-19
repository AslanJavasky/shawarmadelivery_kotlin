package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CqlSessionFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories


//@Configuration
//@EnableCassandraRepositories
class CassandraConfig (
//    private val template:CassandraTemplate
): AbstractCassandraConfiguration() {

//    @Bean
//    fun createIndexes(): Boolean {
//        val cql="CREATE INDEX ON users(email)"
//        return template.cqlOperations.execute(cql)
//    }

    @Value("\${spring.data.cassandra.keyspace-name}")
    private lateinit var keyspaceName: String

    @Value("\${spring.data.cassandra.contact-points}")
    private lateinit var contactPoints: String

    @Value("\${spring.data.cassandra.port}")
    private var port: Int = 2

    @Value("\${spring.data.cassandra.username}")
    private lateinit var username: String

    @Value("\${spring.data.cassandra.password}")
    private lateinit var password: String

    @Value("\${spring.data.cassandra.local-datacenter}")
    private lateinit var datacenter: String


    override fun getLocalDataCenter(): String? {
        return datacenter
    }

    override fun getKeyspaceName(): String {
        return keyspaceName
    }

    override fun getContactPoints(): String {
        return contactPoints
    }

    override fun getPort(): Int {
        return port
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    @Bean
    override fun cassandraSession(): CqlSessionFactoryBean {
        val session = CqlSessionFactoryBean()
        session.setContactPoints(getContactPoints())
        session.setKeyspaceName(getKeyspaceName())
        session.setPort(port)
        session.setUsername(username)
        session.setPassword(password)
        session.setLocalDatacenter(datacenter)
        return session
    }

}