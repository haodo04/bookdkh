package com.haodk.profile.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.haodk.profile.entity.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    Optional<UserProfile> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
