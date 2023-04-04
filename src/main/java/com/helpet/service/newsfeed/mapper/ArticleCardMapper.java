package com.helpet.service.newsfeed.mapper;

import com.helpet.service.newsfeed.dto.response.ArticleCardResponse;
import com.helpet.service.newsfeed.storage.model.ArticleCard;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleCardMapper extends ClassMapper<ArticleCard, ArticleCardResponse> {
}