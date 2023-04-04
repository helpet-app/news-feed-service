package com.helpet.service.newsfeed.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    ACCOUNT_DOES_NOT_EXIST,
    CONTENT_MANAGER_DOES_NOT_EXIST,
    ARTICLE_DOES_NOT_EXIST,
    CONTENT_MANAGER_DOES_NOT_HAVE_THIS_ARTICLE;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}
