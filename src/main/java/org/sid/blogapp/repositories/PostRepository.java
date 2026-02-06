package org.sid.blogapp.repositories;

import org.sid.blogapp.domain.entities.Post;
import org.sid.blogapp.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    




}
