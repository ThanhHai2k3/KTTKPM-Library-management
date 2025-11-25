package com.library.librarymanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    private String description;
}
