package com.helpet.service.newsfeed.dto.request;

import com.helpet.validation.annotation.Word;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class AddArticleTagsRequest {
    @NotEmpty(message = "{validations.not-empty.tags-cannot-be-empty}")
    Set<@Word(message = "{validations.word.tag-must-be-word}") String> tags;
}

