package com.helpet.service.newsfeed.dto.request;

import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateArticleRequest {
    @NotBlank(message = "{validations.not-blank.title-cannot-be-blank}")
    private String title;

    @NotBlank(message = "{validations.not-blank.description-cannot-be-blank}")
    private String description;

    @NotBlank(message = "{validations.not-blank.image-url-cannot-be-blank}")
    @URL(message = "{validations.url.image-url-is-invalid}")
    private String imageUrl;

    @NotBlank(message = "{validations.not-blank.content-cannot-be-blank}")
    private String content;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.source-name-cannot-be-blank-or-must-be-null}")
    private String sourceName;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.source-link-cannot-be-blank-or-must-be-null}")
    @URL(message = "{validations.url.source-link-is-invalid}")
    private String sourceLink;
}
