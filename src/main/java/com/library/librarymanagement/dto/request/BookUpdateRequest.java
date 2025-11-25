package com.library.librarymanagement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateRequest {

    private String title;
    private Long categoryId;
    private String author;
    private String publisher;
    private String description;
}
