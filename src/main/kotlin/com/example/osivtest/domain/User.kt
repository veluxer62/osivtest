package com.example.osivtest.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "\"user\"")
class User(
    name: String,
    age: Int,
) {
    @Id
    @Column(columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val name: String = name

    @Column(nullable = false)
    val age: Int = age

    @OneToMany(mappedBy = "orderer", cascade = [CascadeType.ALL])
    protected val mutableOrders: MutableList<Order> = mutableListOf()
    val orders: List<Order> get() = mutableOrders.toList()

    fun order(order: Order) {
        mutableOrders.add(order)
    }
}
