package com.helpet.service.newsfeed.controller;

import com.helpet.service.newsfeed.dto.request.CreateTagRequest;
import com.helpet.service.newsfeed.mapper.TagMapper;
import com.helpet.service.newsfeed.service.TagService;
import com.helpet.service.newsfeed.storage.model.Tag;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tags")
@RestController
public class TagController {
    private final TagService tagService;

    private final TagMapper tagMapper;

    @Autowired
    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getTags(@RequestParam(value = "name", defaultValue = "") String searchName, Pageable pageable) {
        Page<Tag> tags = tagService.getTags(searchName, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapPage(tags));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTag(@RequestBody @Valid CreateTagRequest request) {
        Tag tag = tagService.createTagIfNeeded(request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.map(tag));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
