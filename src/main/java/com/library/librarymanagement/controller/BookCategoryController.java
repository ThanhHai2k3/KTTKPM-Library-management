package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.request.BookCategoryCreateRequest;
import com.library.librarymanagement.dto.request.BookCategoryUpdateRequest;
import com.library.librarymanagement.dto.response.BookCategoryResponse;
import com.library.librarymanagement.service.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class BookCategoryController {

    private final BookCategoryService categoryService;

    @PostMapping
    public ResponseEntity<BookCategoryResponse> createCategory(@Valid @RequestBody BookCategoryCreateRequest request) {
        BookCategoryResponse response = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCategoryResponse> updateCategory(@PathVariable("id") Long id,
                                                               @Valid @RequestBody BookCategoryUpdateRequest request) {

        BookCategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }
}
