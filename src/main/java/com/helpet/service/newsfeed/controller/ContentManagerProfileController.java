package com.helpet.service.newsfeed.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.newsfeed.dto.request.CreateContentManagerProfileRequest;
import com.helpet.service.newsfeed.dto.request.UpdateContentManagerProfileRequest;
import com.helpet.service.newsfeed.mapper.ContentManagerMapper;
import com.helpet.service.newsfeed.service.ContentManagerProfileService;
import com.helpet.service.newsfeed.storage.model.ContentManager;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RolesAllowed(Role.CONTENT_MANAGER)
@RequestMapping("/content-manager/profile")
@RestController
public class ContentManagerProfileController {
    private final ContentManagerProfileService contentManagerProfileService;

    private final ContentManagerMapper contentManagerMapper;

    @Autowired
    public ContentManagerProfileController(ContentManagerProfileService contentManagerProfileService, ContentManagerMapper contentManagerMapper) {
        this.contentManagerProfileService = contentManagerProfileService;
        this.contentManagerMapper = contentManagerMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getProfile(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        ContentManager profile = contentManagerProfileService.getProfile(contentManagerId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(contentManagerMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createProfile(@RequestBody @Valid CreateContentManagerProfileRequest createContentManagerProfileRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        ContentManager profile = contentManagerProfileService.createProfile(accountId, createContentManagerProfileRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(contentManagerMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseBody> updateProfile(@RequestBody @Valid UpdateContentManagerProfileRequest updateContentManagerProfileRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID contentManagerId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        ContentManager profile = contentManagerProfileService.updateProfile(contentManagerId, updateContentManagerProfileRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(contentManagerMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
