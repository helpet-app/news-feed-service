package com.helpet.service.newsfeed.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddArticleToFavoritesRequest {
    @NotNull(message = "{validations.not-null.article-id-cannot-be-null}")
    private UUID articleId;
}
