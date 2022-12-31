package com.example.osivtest.domain

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, UUID>
