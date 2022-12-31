package com.example.osivtest.domain

import java.time.LocalDate
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatisticsService(
    private val statisticsRepository: StatisticsRepository,
) {
    @Transactional
    fun create(command: CreateStatisticsCommand): Statistics {
        val entity = Statistics(command.date, command.userId, command.orderCount, command.itemCount)
        return statisticsRepository.save(entity)
    }

    @Transactional(readOnly = true)
    fun getStatisticsByUser(userId: UUID): List<Statistics> {
        return statisticsRepository.findByUserId(userId)
    }
}

data class CreateStatisticsCommand(
    val date: LocalDate,
    val userId: UUID,
    val orderCount: Int,
    val itemCount: Int,
)
