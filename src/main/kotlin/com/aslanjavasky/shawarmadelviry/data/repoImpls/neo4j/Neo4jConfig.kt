//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j
//
//import org.neo4j.driver.Driver
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
//import org.springframework.transaction.PlatformTransactionManager
//
//@Configuration
//class Neo4jConfig {
//
//    @Bean
//    fun transactionManager(driver:Driver):PlatformTransactionManager{
//        return Neo4jTransactionManager(driver)
//    }
//}