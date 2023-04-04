package com.helpet.service.newsfeed.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ArticleResponse {
    private UUID id;

    private ArticleCardResponse articleCard;

    private String content;

    private String sourceName;

    private String sourceLink;
}
