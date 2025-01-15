package com.aslanjavasky.shawarmadelviry.domain.model

data class MenuItem(
    var id: Long,
    var name: String,
    var menuSection: MenuSection,
    var price: Int
)
