package com.aslanjavasky.shawarmadelviry.domain.model

import java.math.BigDecimal

data class MenuItem(
    override var id: Long? = -1,
    override var name: String = "",
    override var menuSection: MenuSection = MenuSection.MAIN_MENU,
    override var price: BigDecimal = BigDecimal.ZERO
) : IMenuItem
