package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "menu_items")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
data class MenuItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_section", nullable = false)
    var menuSection: MenuSection = MenuSection.MAIN_MENU,

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO

) : BaseEntity()

fun IMenuItem.toMenuItemEntity() = MenuItemEntity(
    id = this.id,
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)

fun MenuItemEntity.toIMenuItem() = MenuItem(
    id = this.id!!,
    name = this.name,
    menuSection = this.menuSection,
    price = this.price
)
