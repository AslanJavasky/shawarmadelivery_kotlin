package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq

import com.aslanjavasky.shawarmadelviry.domain.model.*
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo
import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables
import com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.*
import com.aslanjavasky.shawarmadelviry.generated.jooq.tables.Deliveries
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository("DRwJOOQ")
class DeliveryRepoImpl(
    private val dslContext: DSLContext,
) : DeliveryRepo {
    override fun saveDelivery(delivery: IDelivery): IDelivery {

        val deliveryId = dslContext.insertInto(DELIVERIES)
            .set(DELIVERIES.ADDRESS, delivery.address)
            .set(DELIVERIES.PHONE, delivery.phone)
            .set(DELIVERIES.DATE_TIME, delivery.dateTime)
            .set(DELIVERIES.ORDER_ID, delivery.order!!.id)
            .returningResult(DELIVERIES.ID)
            .fetchOne()
            ?.get(DELIVERIES.ID) ?: throw RuntimeException("Failed to save delivery, no generated key")

        delivery.id = deliveryId
        return delivery
    }

    override fun updateDelivery(delivery: IDelivery): IDelivery {

        val affectedRow = dslContext.update(DELIVERIES)
            .set(DELIVERIES.ADDRESS, delivery.address)
            .set(DELIVERIES.PHONE, delivery.phone)
            .set(DELIVERIES.DATE_TIME, delivery.dateTime)
            .set(DELIVERIES.ORDER_ID, delivery.order!!.id)
            .where(DELIVERIES.ID.eq(delivery.id))
            .execute()
        if (affectedRow == 0) throw RuntimeException("Failed to update delivery, no rows affected")
        return delivery
    }

    override fun getDeliveryById(id: Long): IDelivery? {


        return dslContext.select(
            Deliveries.DELIVERIES.ID.`as`("delivery_id"),
            Deliveries.DELIVERIES.DATE_TIME.`as`("delivery_date_time"),
            Deliveries.DELIVERIES.ORDER_ID,
            ORDERS.DATE_TIME.`as`("order_date_time"),
            ORDERS.STATUS,
            ORDERS.USER_ID,
            ORDERS.TOTAL_PRICE,
            USERS.NAME.`as`("user_name"),
            USERS.EMAIL,
            USERS.PASSWORD,
            USERS.TELEGRAM,
            USERS.PHONE,
            USERS.ADDRESS,
            USERS.EMAIL,
            ORDERS_MENU_ITEMS.MENU_ITEM_ID,
            MENU_ITEMS.NAME.`as`("menu_item_name"),
            MENU_ITEMS.MENU_SECTION,
            MENU_ITEMS.PRICE
        )
            .from(Deliveries.DELIVERIES)
            .join(ORDERS).on(Deliveries.DELIVERIES.ORDER_ID.eq(ORDERS.ID))
            .join(USERS).on(Tables.ORDERS.USER_ID.eq(USERS.ID))
            .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
            .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
            .where(Deliveries.DELIVERIES.ID.eq(id))
            .orderBy(Deliveries.DELIVERIES.ID)
            .fetchGroups(Deliveries.DELIVERIES.ID)
            .values
            .map { records ->
                var delivery: IDelivery? = null
                for (record in records) {
                    if (delivery == null) {
                        delivery = createDeliveryFromRecord(record)
                        delivery.order = createOrderFromRecord(record)
                        delivery.order!!.user = createUserFromRecord(record)
                        delivery.order!!.itemList = ArrayList<IMenuItem>()
                    }
                    delivery.order!!.itemList!!.add(createMenuItemFromRecord(record))
                }
                delivery
            }
            .get(0)



    }
        private fun createMenuItemFromRecord(record: Record): IMenuItem {
            val menuItem = MenuItem()
            menuItem.id = record.get(Tables.ORDERS_MENU_ITEMS.MENU_ITEM_ID)
            menuItem.name = record.get("menu_item_name", String::class.java)
            menuItem.menuSection = MenuSection.valueOf(record.get(Tables.MENU_ITEMS.MENU_SECTION))
            menuItem.price = record.get(Tables.MENU_ITEMS.PRICE)
            return menuItem
        }

        private fun createUserFromRecord(record: Record): IUser {
            val user: IUser = User()
            user.id = record.get(Tables.ORDERS.USER_ID)
            user.name = record.get("user_name", String::class.java)
            user.email = record.get(Tables.USERS.EMAIL)
            user.password = record.get(Tables.USERS.PASSWORD)
            user.telegram = record.get(Tables.USERS.TELEGRAM)
            user.phone = record.get(Tables.USERS.PHONE)
            user.address = record.get(Tables.USERS.ADDRESS)
            return user
        }

        private fun createOrderFromRecord(record: Record): IOrder {
            val newOrder: IOrder = Order()
            newOrder.id = record.get("order_id", Long::class.java)
            newOrder.dateTime = record.get("order_date_time", LocalDateTime::class.java)
            newOrder.status = OrderStatus.valueOf(record.get(Tables.ORDERS.STATUS))
            newOrder.totalPrice = record.get(Tables.ORDERS.TOTAL_PRICE)
            return newOrder
        }

        private fun createDeliveryFromRecord(record: Record): IDelivery {
            val delivery: IDelivery = Delivery()
            delivery.id = record.get("delivery_id", Long::class.java)
            delivery.address = record.get(Deliveries.DELIVERIES.ADDRESS)
            delivery.phone = record.get(Deliveries.DELIVERIES.PHONE)
            delivery.dateTime = record.get("delivery_date_time", LocalDateTime::class.java)
            return delivery
        }
    }