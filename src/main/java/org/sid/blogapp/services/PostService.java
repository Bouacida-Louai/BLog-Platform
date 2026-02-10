package org.sid.blogapp.services;

import org.sid.blogapp.domain.CreatePostRequest;
import org.sid.blogapp.domain.UpdatePostRequest;
import org.sid.blogapp.domain.entities.Post;
import org.sid.blogapp.domain.entities.User;

import java.util.List;
import java.util.UUID;


public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    List<Post> getDraftPosts(User User);

    Post createPost(User user , CreatePostRequest createPostRequest);

    Post updatePost(UUID postId, UpdatePostRequest updatePostRequest);

    Post getPosts(UUID postId);


}
