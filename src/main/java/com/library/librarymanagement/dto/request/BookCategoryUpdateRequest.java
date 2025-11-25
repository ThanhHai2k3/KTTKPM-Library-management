package com.library.librarymanagement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategoryUpdateRequest {

    private String name;
    private String description;
}
