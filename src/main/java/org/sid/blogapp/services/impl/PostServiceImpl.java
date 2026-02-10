package org.sid.blogapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.PostStatus;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.domain.entities.Post;
import org.sid.blogapp.domain.entities.Tag;
import org.sid.blogapp.domain.entities.User;
import org.sid.blogapp.repositories.PostRepository;
import org.sid.blogapp.services.CategoryService;
import org.sid.blogapp.services.PostService;
import org.sid.blogapp.services.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if(categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if(tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User User) {
        return postRepository.findAllByAuthorAndStatus(User, PostStatus.DRAFT);
    }


}
