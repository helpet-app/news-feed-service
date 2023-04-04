package com.helpet.service.newsfeed.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.newsfeed.dto.request.CreateContentManagerProfileRequest;
import com.helpet.service.newsfeed.dto.request.UpdateContentManagerProfileRequest;
import com.helpet.service.newsfeed.service.error.ConflictLocalizedError;
import com.helpet.service.newsfeed.service.error.NotFoundLocalizedError;
import com.helpet.service.newsfeed.storage.model.Account;
import com.helpet.service.newsfeed.storage.model.ContentManager;
import com.helpet.service.newsfeed.storage.repository.ContentManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContentManagerProfileService {
    private final AccountService accountService;

    private final ContentManagerRepository contentManagerRepository;

    @Autowired
    public ContentManagerProfileService(AccountService accountService, ContentManagerRepository contentManagerRepository) {
        this.accountService = accountService;
        this.contentManagerRepository = contentManagerRepository;
    }

    public boolean contentManagerExists(UUID contentManagerId) {
        return contentManagerRepository.existsById(contentManagerId);
    }

    public ContentManager getProfile(UUID contentManagerId) throws NotFoundLocalizedException {
        return contentManagerRepository.findContentManagerById(contentManagerId)
                                       .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.CONTENT_MANAGER_DOES_NOT_EXIST));
    }

    public ContentManager createProfile(UUID accountId,
                                        CreateContentManagerProfileRequest profileInfo) throws NotFoundLocalizedException, ConflictLocalizedException {
        Account account = accountService.getAccount(accountId);

        if (contentManagerExists(accountId)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.CONTENT_MANAGER_WITH_SUCH_ID_ALREADY_EXISTS);
        }

        ContentManager newProfile = ContentManager.builder()
                                                  .account(account)
                                                  .name(profileInfo.getName())
                                                  .build();

        return contentManagerRepository.save(newProfile);
    }

    public ContentManager updateProfile(UUID contentManagerId, UpdateContentManagerProfileRequest profileInfo) throws NotFoundLocalizedException {
        ContentManager profile = getProfile(contentManagerId);

        profile.setName(profileInfo.getName());

        return contentManagerRepository.save(profile);
    }
}
