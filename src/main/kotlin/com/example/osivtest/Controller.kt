package com.example.osivtest

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    private val facade: Facade,
) {
    @GetMapping("/users")
    fun getAllUsers(): List<UserDto> {
        return facade.getAllUsers().map { UserDto(it.id, it.name, it.age) }
    }

    @GetMapping("/orders/{userId}")
    fun getOrdersByUser(@PathVariable userId: UUID): List<OrderDto> {
        return facade.getOrdersByUser(userId).map {
            val items = it.items.map { item -> ItemDto(item.id, item.name) }
            val user = UserDto(it.orderer.id, it.orderer.name, it.orderer.age)
            OrderDto(it.id, it.createdAt, items, user)
        }
    }

    @GetMapping("statistics/{userId}")
    fun getStatistics(@PathVariable userId: UUID): List<StatisticsDto> {
        return facade.getStatisticsByUser(userId).map {
            StatisticsDto(it.userId, it.date, it.orderCount, it.itemCount)
        }
    }
}

data class OrderDto(
    val id: UUID,
    val createdAt: LocalDateTime,
    val items: List<ItemDto>,
    val orderer: UserDto,
)

data class ItemDto(
    val id: UUID,
    val name: String,
)

data class UserDto(
    val id: UUID,
    val name: String,
    val age: Int,
)

data class StatisticsDto(
    val userId: UUID,
    val date: LocalDate,
    val orderCount: Int,
    val itemCount: Int,
)
