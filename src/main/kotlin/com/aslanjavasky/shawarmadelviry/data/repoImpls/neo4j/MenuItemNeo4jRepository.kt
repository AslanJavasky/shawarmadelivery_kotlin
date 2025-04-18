//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j
//
//import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.MenuItemEntity
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
//import org.springframework.data.neo4j.repository.Neo4jRepository
//import org.springframework.stereotype.Repository
//import java.util.*
//
//@Repository
//interface MenuItemNeo4jRepository : Neo4jRepository<MenuItemEntity, UUID>{
//    fun findByMenuSection(section: MenuSection): List<MenuItemEntity>
//}