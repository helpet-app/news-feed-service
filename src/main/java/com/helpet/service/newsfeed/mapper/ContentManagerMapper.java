package com.helpet.service.newsfeed.mapper;

import com.helpet.service.newsfeed.dto.response.ContentManagerResponse;
import com.helpet.service.newsfeed.storage.model.ContentManager;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentManagerMapper extends ClassMapper<ContentManager, ContentManagerResponse> {
}