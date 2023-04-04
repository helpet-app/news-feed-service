package com.helpet.service.newsfeed.dto.request;

import com.helpet.validation.annotation.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTagRequest {
    @Word(message = "{validations.word.tag-must-be-word}")
    private String name;
}
