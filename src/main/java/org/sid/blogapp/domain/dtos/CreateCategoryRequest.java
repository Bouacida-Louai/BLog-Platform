package org.sid.blogapp.domain.dtos;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder@NoArgsConstructor@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank(message = "Category name must not be blank")
    @Size(min = 2, max = 50, message = "Category name must be between {min} and {max} characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Category name must contain only letters, numbers, and spaces")
    private String name;


}
