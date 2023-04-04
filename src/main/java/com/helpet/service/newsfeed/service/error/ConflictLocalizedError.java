package com.helpet.service.newsfeed.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    CONTENT_MANAGER_WITH_SUCH_ID_ALREADY_EXISTS;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
