package com.helpet.service.newsfeed.dto.request;

import com.helpet.validation.annotation.Name;
import lombok.Data;

@Data
public class UpdateContentManagerProfileRequest {
    @Name(message = "{validations.name.name-is-invalid}")
    private String name;
}
