package com.example.osivtest

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.orm.jpa.EntityManagerFactoryAccessor
import org.springframework.orm.jpa.EntityManagerFactoryUtils
import org.springframework.orm.jpa.EntityManagerHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager

@Component
@Aspect
class OpenEntityManagerInSchedulerAspect : EntityManagerFactoryAccessor() {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Around("@annotation(OpenEntityManagerInScheduler)")
    fun openEntityManager(pjp: ProceedingJoinPoint) {
        val emf: EntityManagerFactory = obtainEntityManagerFactory()
        openEntity(emf)

        try {
            pjp.proceed()
        } finally {
            closeEntity(emf)
        }
    }

    private fun openEntity(emf: EntityManagerFactory) {
        log.info("Opening JPA EntityManager in SchedulerOpenEntityManagerAspect")
        try {
            val em: EntityManager = createEntityManager()
            val emHolder = EntityManagerHolder(em)
            TransactionSynchronizationManager.bindResource(emf, emHolder)
        } catch (ex: PersistenceException) {
            throw DataAccessResourceFailureException("Could not create JPA EntityManager", ex)
        }
    }

    private fun closeEntity(emf: EntityManagerFactory) {
        log.info("Closing JPA EntityManager in SchedulerOpenEntityManagerAspect")
        val emHolder = TransactionSynchronizationManager.unbindResource(emf) as EntityManagerHolder
        EntityManagerFactoryUtils.closeEntityManager(emHolder.entityManager)
    }
}
