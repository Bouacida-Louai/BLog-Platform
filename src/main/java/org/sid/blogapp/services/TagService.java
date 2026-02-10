package org.sid.blogapp.services;

import org.sid.blogapp.domain.dtos.CreateTagsRequest;
import org.sid.blogapp.domain.entities.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getTags();

    List<Tag> createTags(Set<String> tagNames);

    void deleteTags(UUID id);
    Tag getTagById(UUID id);

    List<Tag> getTagsByIds(Set<UUID> tagIds);
}