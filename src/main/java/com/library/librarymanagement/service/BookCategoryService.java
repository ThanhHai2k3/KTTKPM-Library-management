package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.BookCategoryCreateRequest;
import com.library.librarymanagement.dto.request.BookCategoryUpdateRequest;
import com.library.librarymanagement.dto.response.BookCategoryResponse;

import java.util.List;

public interface BookCategoryService {

    BookCategoryResponse create(BookCategoryCreateRequest request);

    BookCategoryResponse update(Long id, BookCategoryUpdateRequest request);

    void delete(Long id);

    List<BookCategoryResponse> getAll();

    BookCategoryResponse getById(Long id);

    boolean existsByName(String name);
}
