package com.example.platformapi.persistence.repository

import com.example.platformapi.persistence.model.SessionInfo
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<SessionInfo, String>
