package com.helpet.service.newsfeed.service;

import com.helpet.service.newsfeed.dto.request.CreateTagRequest;
import com.helpet.service.newsfeed.storage.model.Tag;
import com.helpet.service.newsfeed.storage.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Page<Tag> getTags(String searchName, Pageable pageable) {
        return tagRepository.findAllByNameContainingIgnoreCaseOrderByName(searchName, pageable);
    }

    public List<Tag> getTagsInNames(Set<String> names) {
        return tagRepository.findAllByNameIn(names);
    }

    public Tag createTagIfNeeded(CreateTagRequest tagInfo) {
        Optional<Tag> tag = tagRepository.findTagByName(tagInfo.getName());

        if (tag.isPresent()) {
            return tag.get();
        }

        Tag newTag = Tag.builder()
                        .name(tagInfo.getName())
                        .build();

        return tagRepository.save(newTag);
    }

    public List<Tag> createTagsIfNeeded(Set<CreateTagRequest> tagsInfo) {
        Set<String> tagNames = tagsInfo.stream().map(CreateTagRequest::getName).collect(Collectors.toSet());
        List<Tag> tags = tagRepository.findAllByNameIn(tagNames);
        for (Tag tag : tags) {
            tagNames.removeIf(tagName -> tagName.equalsIgnoreCase(tag.getName()));
        }

        Set<Tag> newTags = tagNames.stream()
                                   .map(tagName -> Tag.builder()
                                                      .name(tagName)
                                                      .build())
                                   .collect(Collectors.toSet());

        tags.addAll(tagRepository.saveAll(newTags));

        return tags;
    }
}
