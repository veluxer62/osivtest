package com.example.osivtest

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class StatisticsScheduler(
    private val facade: Facade,
) {
    @OpenEntityManagerInScheduler
    @Scheduled(cron = "0 0 2 * * *")
    fun createDailyStatistics() {
        facade.createDailyStatistics()
    }
}

