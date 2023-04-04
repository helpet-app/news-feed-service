package com.helpet.service.newsfeed.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ContentManagerResponse {
    private UUID id;

    private String name;

    private String avatarUrl;
}
