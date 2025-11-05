package com.haodk.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haodk.identity.entity.InvalidateToken;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {}
