package com.library.librarymanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategoryResponse {

    private Long id;
    private String name;
    private String description;
    private int bookCount;
}
