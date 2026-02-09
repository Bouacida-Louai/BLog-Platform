package org.sid.blogapp.controller;


import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.dtos.CreateTagsRequest;
import org.sid.blogapp.domain.dtos.TagResponse;

import org.sid.blogapp.domain.entities.Tag;
import org.sid.blogapp.mappers.TagMapper;
import org.sid.blogapp.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {

    List<Tag> savedTags= tagService.createTags(createTagsRequest.getNames());
    List<TagResponse> createdTagResponses = savedTags.stream().map(tagMapper::toTagResponse).toList();
    return new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTags(id);
        return ResponseEntity.noContent().build();

    }

}


