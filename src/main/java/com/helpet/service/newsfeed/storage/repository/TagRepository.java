package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Page<Tag> findAllByNameContainingIgnoreCaseOrderByName(String searchName, Pageable pageable);

    List<Tag> findAllByNameIn(Collection<String> names);

    Optional<Tag> findTagByNameIgnoreCase(String name);
}
