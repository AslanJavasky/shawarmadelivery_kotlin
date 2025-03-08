package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity

import com.aslanjavasky.shawarmadelviry.domain.model.*
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "orders")
data class OrderEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "date_time", nullable = false)
    var dateTime: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.NEW,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity = UserEntity(),

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "orders_menu_items",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "menu_item_id")]
    )
    var itemList: MutableList<MenuItemEntity>? = mutableListOf()
) : BaseEntity()

fun IOrder.toOrderEntity() = OrderEntity(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = this.user!!.toUserEntity(),
    totalPrice = this.totalPrice!!,
    itemList = this.itemList!!.map { it.toMenuItemEntity() }.toMutableList()
)

fun OrderEntity.toIOrder() = Order(
    id = this.id,
    dateTime = this.dateTime!!,
    status = this.status!!,
    user = user.toIUser(),
    itemList = itemList!!.map { it.toIMenuItem() }.toMutableList(),
    totalPrice = this.totalPrice!!
)
