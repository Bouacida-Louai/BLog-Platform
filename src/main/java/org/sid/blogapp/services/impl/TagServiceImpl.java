package org.sid.blogapp.services.impl;


import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.Tag;
import org.sid.blogapp.repositories.TagRepository;
import org.sid.blogapp.services.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

}
