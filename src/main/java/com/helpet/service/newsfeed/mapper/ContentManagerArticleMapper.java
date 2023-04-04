package com.helpet.service.newsfeed.mapper;

import com.helpet.service.newsfeed.dto.response.ContentManagerArticleResponse;
import com.helpet.service.newsfeed.storage.model.Article;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentManagerArticleMapper extends ClassMapper<Article, ContentManagerArticleResponse> {
}