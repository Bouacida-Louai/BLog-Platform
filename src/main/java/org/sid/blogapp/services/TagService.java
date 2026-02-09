package org.sid.blogapp.services;

import org.sid.blogapp.domain.entities.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();
}