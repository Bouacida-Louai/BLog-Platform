package org.sid.blogapp.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.sid.blogapp.domain.PostStatus;
import org.sid.blogapp.domain.dtos.TagResponse;
import org.sid.blogapp.domain.entities.Post;
import org.sid.blogapp.domain.entities.Tag;


import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
   @Mapping(target = "postCount" , source = "posts",qualifiedByName = "calculatePostCount")
    TagResponse toTagResponse(Tag tag);

   @Named("calculatePostCount")
   default Integer calculatePostCount(Set<Post> posts){
       if (posts==null){
           return 0;
       }
      return (int) posts.stream().filter(post -> post.getStatus().equals(PostStatus.PUBLISHED)).count();

   }
}


