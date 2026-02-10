package org.sid.blogapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.CreatePostRequest;
import org.sid.blogapp.domain.UpdatePostRequest;
import org.sid.blogapp.domain.dtos.CreatePostRequestDto;
import org.sid.blogapp.domain.dtos.PostDto;
import org.sid.blogapp.domain.dtos.UpdatePostRequestDto;
import org.sid.blogapp.domain.entities.Post;
import org.sid.blogapp.domain.entities.User;
import org.sid.blogapp.mappers.PostMapper;
import org.sid.blogapp.services.PostService;
import org.sid.blogapp.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private  final UserService userService;


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {

      User  logedInUser =userService.getUserById(userId);
      List<Post> draftPosts = postService.getDraftPosts(logedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto,
                                              @RequestAttribute UUID userId) {
        User  logedInUser =userService.getUserById(userId);
        CreatePostRequest createPostRequest= postMapper.toCreatePostRequest(createPostRequestDto);

      Post createdPost=  postService.createPost(logedInUser,createPostRequest);

      PostDto postDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(postDto,HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid  @RequestBody UpdatePostRequestDto updatePostRequestDto){
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
       Post post= postService.updatePost(id, updatePostRequest);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
        Post post = postService.getPosts(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);

    }

}
