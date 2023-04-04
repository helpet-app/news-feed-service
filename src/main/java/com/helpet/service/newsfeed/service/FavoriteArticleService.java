package com.helpet.service.newsfeed.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.newsfeed.dto.request.AddArticleToFavoritesRequest;
import com.helpet.service.newsfeed.service.error.NotFoundLocalizedError;
import com.helpet.service.newsfeed.storage.model.Account;
import com.helpet.service.newsfeed.storage.model.AccountFavorite;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.service.newsfeed.storage.repository.AccountFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FavoriteArticleService {
    private final AccountService accountService;

    private final ArticleService articleService;

    private final AccountFavoriteRepository accountFavoriteRepository;

    @Autowired
    public FavoriteArticleService(AccountService accountService, ArticleService articleService, AccountFavoriteRepository accountFavoriteRepository) {
        this.accountService = accountService;
        this.articleService = articleService;
        this.accountFavoriteRepository = accountFavoriteRepository;
    }

    public Page<ArticleCard> getFavoriteArticles(UUID accountId, Pageable pageable) throws NotFoundLocalizedException {
        if (!accountService.accountExists(accountId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return accountFavoriteRepository.findAllFavoriteArticles(accountId, pageable).map(AccountFavorite::getArticleCard);
    }

    public void addArticleToFavorites(UUID accountId, AddArticleToFavoritesRequest articleInfo) throws NotFoundLocalizedException {
        Account account = accountService.getAccount(accountId);

        ArticleCard articleCard = articleService.getArticleCard(articleInfo.getArticleId());

        AccountFavorite newFavoriteArticle = AccountFavorite.builder()
                                                            .id(AccountFavorite.Id.builder()
                                                                                  .accountId(accountId)
                                                                                  .articleId(articleInfo.getArticleId())
                                                                                  .build())
                                                            .account(account)
                                                            .articleCard(articleCard)
                                                            .build();

        accountFavoriteRepository.save(newFavoriteArticle);
    }

    public void deleteArticleFromFavorites(UUID accountId, UUID articleId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(accountId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        if (!articleService.articleExists(articleId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ARTICLE_DOES_NOT_EXIST);
        }

        accountFavoriteRepository.deleteById(AccountFavorite.Id.builder()
                                                               .accountId(accountId)
                                                               .articleId(articleId)
                                                               .build());
    }
}
