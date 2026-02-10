package org.sid.blogapp.services.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.Tag;
import org.sid.blogapp.repositories.TagRepository;
import org.sid.blogapp.services.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTags(UUID id) {
        tagRepository.findById(id).ifPresent(tag ->{
            if (!tag.getPosts().isEmpty()){
                throw  new IllegalStateException("can't delete tags with posts associated with ");
            }
            tagRepository.deleteById(id);
        });

    }

    @Override
    public Tag getTagById(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Tag with id '"+id+"' does not exist")
        );
        return tag;

    }


}
