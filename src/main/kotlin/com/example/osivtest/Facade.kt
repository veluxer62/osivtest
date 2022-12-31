package com.example.osivtest

import com.example.osivtest.domain.CreateStatisticsCommand
import com.example.osivtest.domain.Order
import com.example.osivtest.domain.Statistics
import com.example.osivtest.domain.StatisticsService
import com.example.osivtest.domain.User
import com.example.osivtest.domain.UserService
import java.time.LocalDate
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Facade(
    private val userService: UserService,
    private val statisticsService: StatisticsService,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getOrdersByUser(userId: UUID): List<Order> {
        val user = userService.getById(userId)
        return user.orders
    }

    fun getAllUsers(): List<User> {
        return userService.getAll()
    }

    fun createDailyStatistics() {
        val today = LocalDate.now()
        val users = userService.getAll()
        users.forEach {
            createStatisticsByUser(it, today)
        }
    }

    private fun createStatisticsByUser(user: User, today: LocalDate) {
        try {
            val command = createStatisticsCommand(user, today)
            statisticsService.create(command)
        } catch (e: Exception) {
            log.warn("User[${user.id}] statistics creation failed.")
        }
    }

    private fun createStatisticsCommand(
        user: User,
        today: LocalDate,
    ): CreateStatisticsCommand {
        val todaysOrders = user.orders.filter { it.createdAt.toLocalDate() == today }
        val todaysItems = todaysOrders.flatMap { it.items }
        return CreateStatisticsCommand(
            date = today,
            userId = user.id,
            orderCount = todaysOrders.size,
            itemCount = todaysItems.size,
        )
    }

    fun getStatisticsByUser(userId: UUID): List<Statistics> {
        return statisticsService.getStatisticsByUser(userId)
    }
}
