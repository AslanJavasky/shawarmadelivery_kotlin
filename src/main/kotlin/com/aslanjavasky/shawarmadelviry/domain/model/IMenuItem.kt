package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal

interface IMenuItem {
    var id: Long?
    var name: String
    var menuSection: MenuSection
    var price: BigDecimal
}