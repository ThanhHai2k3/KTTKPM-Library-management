package com.library.librarymanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String description;

    private Long categoryId;
    private String categoryName;
}
