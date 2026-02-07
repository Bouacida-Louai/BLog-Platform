package org.sid.blogapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.sid.blogapp.domain.PostStatus;
import org.sid.blogapp.domain.dtos.CategoryDto;
import org.sid.blogapp.domain.dtos.CreateCategoryRequest;
import org.sid.blogapp.domain.entities.Category;
import org.sid.blogapp.domain.entities.Post;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {


    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Mapping(target = "postCount",source = "posts",qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts){
        if (posts==null){
            return 0;
        }
         return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }


}
