package com.helpet.service.newsfeed.storage.repository;

import com.helpet.service.newsfeed.storage.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(UUID accountId);
}
