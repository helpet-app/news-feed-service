package com.helpet.service.newsfeed.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class ContentManagerArticleCardResponse {
    private UUID id;

    private String title;

    private String description;

    private String imageUrl;

    private OffsetDateTime createdAt;

    private Set<TagResponse> tags;
}
