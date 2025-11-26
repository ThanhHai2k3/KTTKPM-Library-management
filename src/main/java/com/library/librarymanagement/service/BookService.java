package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.BookCreateRequest;
import com.library.librarymanagement.dto.request.BookUpdateRequest;
import com.library.librarymanagement.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponse create(BookCreateRequest request);

    BookResponse update(Long id, BookUpdateRequest request);

    void delete(Long id);

    BookResponse getById(Long id);

    Page<BookResponse> getAll(Pageable pageable);

    Page<BookResponse> search(String keyword, Pageable pageable);
}
