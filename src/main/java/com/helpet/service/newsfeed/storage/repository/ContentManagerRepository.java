package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.ContentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContentManagerRepository extends JpaRepository<ContentManager, UUID> {
    Optional<ContentManager> findContentManagerById(UUID contentManagerId);
}
