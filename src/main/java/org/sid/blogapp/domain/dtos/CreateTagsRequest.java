package org.sid.blogapp.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class CreateTagsRequest {
    @NotEmpty(message = "at least one tag name is required")

    Set<  @Size(max = 10 , message = "Maximum {max} tags allowed")
    @Pattern(regexp = "^[\\w\\s-]+$",message = "tag name can only contain letters,numbers,spaces,hyphens")
     String> names;






}
