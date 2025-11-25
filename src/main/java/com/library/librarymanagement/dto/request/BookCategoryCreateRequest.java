package com.library.librarymanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategoryCreateRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;
}
