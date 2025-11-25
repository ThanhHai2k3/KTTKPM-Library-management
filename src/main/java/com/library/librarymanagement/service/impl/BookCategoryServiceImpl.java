package com.library.librarymanagement.service.impl;

import com.library.librarymanagement.dto.request.BookCategoryCreateRequest;
import com.library.librarymanagement.dto.request.BookCategoryUpdateRequest;
import com.library.librarymanagement.dto.response.BookCategoryResponse;
import com.library.librarymanagement.entity.BookCategory;
import com.library.librarymanagement.repository.BookCategoryRepository;
import com.library.librarymanagement.service.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository categoryRepository;

    @Override
    public BookCategoryResponse create(BookCategoryCreateRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        BookCategory category = BookCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        BookCategory saved = categoryRepository.save(category);

        return mapToResponse(saved);
    }

    @Override
    public BookCategoryResponse update(Long id, BookCategoryUpdateRequest request) {
        BookCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            if (categoryRepository.existsByNameIgnoreCase(request.getName())
                    && !request.getName().equalsIgnoreCase(category.getName())) {
                throw new RuntimeException("Category name already exists");
            }
            category.setName(request.getName());
        }

        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        BookCategory updated = categoryRepository.save(category);

        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookCategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookCategoryResponse getById(Long id) {
        BookCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToResponse(category);
    }

    private BookCategoryResponse mapToResponse(BookCategory category) {
        return new BookCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
