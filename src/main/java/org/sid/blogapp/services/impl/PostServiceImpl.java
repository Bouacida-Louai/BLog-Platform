package org.sid.blogapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.CreatePostRequest;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE=200;
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

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost= new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setAuthor(user);
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setReadTime(calculateReadTime(createPostRequest.getContent()));
        Category category=categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds=createPostRequest.getTagIds();
        List<Tag> tags= tagService.getTagsByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }
    private Integer calculateReadTime(String content) {
          if (content==null || content.isEmpty()){
              return 0;
          }
          int wordCount=content.split("\\s+").length;
       return  (int)Math.ceil( (double) wordCount/WORDS_PER_MINUTE );

    }


}
