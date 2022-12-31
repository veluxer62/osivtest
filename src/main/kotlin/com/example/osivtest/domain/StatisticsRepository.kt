package com.example.osivtest.domain

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface StatisticsRepository : CrudRepository<Statistics, UUID> {
    fun findByUserId(userId: UUID): List<Statistics>
}
