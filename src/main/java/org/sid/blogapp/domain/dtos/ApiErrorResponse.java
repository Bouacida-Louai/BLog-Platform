package org.sid.blogapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.List;

@Data
@Builder
@NoArgsConstructor@AllArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String message;
    List<FieldError> errors;

    @Data@Builder@NoArgsConstructor@AllArgsConstructor
    public static class fieldError{
        private String field;
        private String message;
    }

}
