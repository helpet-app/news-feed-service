package com.helpet.service.newsfeed.mapper;

import com.helpet.service.newsfeed.dto.response.TagResponse;
import com.helpet.service.newsfeed.storage.model.Tag;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper extends ClassMapper<Tag, TagResponse> {
}

