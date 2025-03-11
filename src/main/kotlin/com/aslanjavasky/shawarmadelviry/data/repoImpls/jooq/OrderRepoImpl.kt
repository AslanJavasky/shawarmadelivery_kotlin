//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq
//
//import com.aslanjavasky.shawarmadelviry.domain.model.*
//import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo
//import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.*
//import org.jooq.DSLContext
//import org.jooq.Record
//import org.springframework.stereotype.Repository
//
//@Repository("ORwJOOQ")
//class OrderRepoImpl(
//    private val dslContext: DSLContext
//) : OrderRepo {
//
//    override fun saveOrder(order: IOrder): IOrder {
//
//        val orderId = dslContext.insertInto(ORDERS)
//            .set(ORDERS.DATE_TIME, order.dateTime)
//            .set(ORDERS.STATUS, order.status!!.name)
//            .set(ORDERS.USER_ID, order.user!!.id)
//            .set(ORDERS.TOTAL_PRICE, order.totalPrice)
//            .returningResult(ORDERS.ID)
//            .fetchOne()
//            ?.get(ORDERS.ID)
//
//        order.id = orderId ?: throw RuntimeException("Failed to save orders, no generated key")
//
//        val queryCollection = order.itemList!!.map { item ->
//            dslContext.insertInto(ORDERS_MENU_ITEMS)
//                .set(ORDERS_MENU_ITEMS.ORDER_ID, order.id)
//                .set(ORDERS_MENU_ITEMS.MENU_ITEM_ID, item.id)
//        }
//        dslContext.batch(queryCollection).execute()
//        return order
//    }
//
//
//    override fun updateOrder(order: IOrder): IOrder {
//
//        val affectedRow = dslContext.update(ORDERS)
//            .set(ORDERS.DATE_TIME, order.dateTime)
//            .set(ORDERS.STATUS, order.status!!.name)
//            .set(ORDERS.USER_ID, order.user!!.id)
//            .set(ORDERS.TOTAL_PRICE, order.totalPrice)
//            .where(ORDERS.ID.eq(order.id))
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
//        return order
//    }
//
//    override fun getOrdersByUser(user: IUser): List<IOrder> {
//
//        return dslContext.select(
//            ORDERS.ID.`as`("order_id"),
//            USERS.ID.`as`("user_id"),
//            USERS.NAME.`as`("user_name"),
//            USERS.EMAIL,
//            USERS.PASSWORD,
//            USERS.TELEGRAM,
//            USERS.PHONE,
//            USERS.ADDRESS,
//            ORDERS.DATE_TIME,
//            ORDERS.STATUS,
//            ORDERS.TOTAL_PRICE,
//            ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//            MENU_ITEMS.NAME.`as`("menu_item_name"),
//            MENU_ITEMS.MENU_SECTION,
//            MENU_ITEMS.PRICE,
//        )
//            .from(ORDERS)
//            .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//            .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//            .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
//            .where(USERS.ID.eq(user.id))
//            .orderBy(ORDERS.ID)
//            .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//            .values
//            .map { records ->
//                var order: IOrder? = null
//                for (record in records) {
//                    if (order == null) {
//                        order = createOrderFromRecord(record)
//                        order.user = createUserFromRecord(record)
//                        order.itemList = ArrayList()
//                    }
//                    order.itemList!!.add(createMenuItemFromRecord(record))
//                }
//                order!!
//            }
//    }
//
//
//    override fun getOrdersByStatus(orderStatus: OrderStatus): List<IOrder> {
//
//        return dslContext.select(
//            USERS.ID.`as`("user_id"),
//            USERS.NAME.`as`("user_name"),
//            USERS.EMAIL,
//            USERS.PASSWORD,
//            USERS.TELEGRAM,
//            USERS.PHONE,
//            USERS.ADDRESS,
//            ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//            MENU_ITEMS.NAME.`as`("menu_item_name"),
//            MENU_ITEMS.MENU_SECTION,
//            MENU_ITEMS.PRICE,
//            ORDERS_MENU_ITEMS.ORDER_ID,
//            ORDERS.DATE_TIME,
//            ORDERS.STATUS,
//            ORDERS.TOTAL_PRICE
//        )
//            .from(ORDERS)
//            .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//            .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//            .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
//            .where(ORDERS.STATUS.eq(orderStatus.name))
//            .orderBy(ORDERS.ID)
//            .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//            .values
//            .map { records ->
//                var order: IOrder? = null
//                for (record in records) {
//                    if (order == null) {
//                        order = createOrderFromRecord(record)
//                        order.user = createUserFromRecord(record)
//                        order.itemList = ArrayList()
//                    }
//                    order.itemList!!.add(createMenuItemFromRecord(record))
//                }
//                order!!
//            }
//    }
//
//
//    override fun updateOrderStatus(id: Long, status: OrderStatus): IOrder {
//
//        val affectedRow = dslContext.update(ORDERS)
//            .set(ORDERS.STATUS, status.name)
//            .where(ORDERS.ID.eq(id))
//            .execute()
//        if (affectedRow == 0) throw RuntimeException("Failed to update order, no rows affected")
//        return getOrderById(id)!!
//    }
//
//    fun getOrderById(orderId: Long): IOrder? {
//
//        return dslContext.select(
//            USERS.ID.`as`("user_id"),
//            USERS.NAME.`as`("user_name"),
//            USERS.EMAIL,
//            USERS.PASSWORD,
//            USERS.TELEGRAM,
//            USERS.PHONE,
//            USERS.ADDRESS,
//            ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//            MENU_ITEMS.NAME.`as`("menu_item_name"),
//            MENU_ITEMS.MENU_SECTION,
//            MENU_ITEMS.PRICE,
//            ORDERS_MENU_ITEMS.ORDER_ID,
//            ORDERS.DATE_TIME,
//            ORDERS.STATUS,
//            ORDERS.TOTAL_PRICE
//        )
//            .from(ORDERS)
//            .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//            .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//            .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
//            .where(ORDERS_MENU_ITEMS.ORDER_ID.eq(orderId))
//            .orderBy(ORDERS.ID)
//            .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//            .values
//            .stream()
//            .map { records ->
//                var order: IOrder? = null
//                for (record in records) {
//                    if (order == null) {
//                        order = createOrderFromRecord(record)
//                        order.user = createUserFromRecord(record)
//                        order.itemList = ArrayList()
//                    }
//                    order.itemList!!.add(createMenuItemFromRecord(record))
//                }
//                order!!
//            }
//            .findFirst().orElse(null)
//
//    }
//
//    private fun createMenuItemFromRecord(record: Record): IMenuItem {
//        val menuItem = MenuItem()
//        menuItem.id = record.get(ORDERS_MENU_ITEMS.MENU_ITEM_ID)
//        menuItem.name = record.get("menu_item_name", String::class.java)
//        menuItem.menuSection = MenuSection.valueOf(record.get(MENU_ITEMS.MENU_SECTION))
//        menuItem.price = record.get(MENU_ITEMS.PRICE)
//        return menuItem
//    }
//
//
//    private fun createUserFromRecord(record: Record): IUser {
//        val user: IUser = User()
//        user.id = record.get("user_id", Long::class.java)
//        user.name = record.get("user_name", String::class.java)
//        user.email = record.get(USERS.EMAIL)
//        user.password = record.get(USERS.PASSWORD)
//        user.telegram = record.get(USERS.TELEGRAM)
//        user.phone = record.get(USERS.PHONE)
//        user.address = record.get(USERS.ADDRESS)
//        return user
//    }
//
//    private fun createOrderFromRecord(record: Record): IOrder {
//        val order: IOrder = Order()
//        order.id = record.get("order_id", Long::class.java)
//        order.dateTime = record.get(ORDERS.DATE_TIME)
//        order.status = OrderStatus.valueOf(record.get(ORDERS.STATUS))
//        order.totalPrice = record.get(ORDERS.TOTAL_PRICE)
//        return order
//    }
//
//}