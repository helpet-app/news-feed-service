package com.helpet.service.newsfeed.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ContentManagerArticleResponse {
    private UUID id;

    private ContentManagerArticleCardResponse articleCard;

    private String content;

    private String sourceName;

    private String sourceLink;
}
