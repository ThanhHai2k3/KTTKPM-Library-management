package com.library.librarymanagement.service.impl;

import com.library.librarymanagement.dto.request.BookCreateRequest;
import com.library.librarymanagement.dto.request.BookUpdateRequest;
import com.library.librarymanagement.dto.response.BookResponse;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BookCategory;
import com.library.librarymanagement.repository.BookCategoryRepository;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository categoryRepository;

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getDescription(),
                book.getCategory().getId(),
                book.getCategory().getName()
        );
    }

    @Override
    @Transactional
    public BookResponse create(BookCreateRequest request) {
        if (bookRepository.existsByTitleIgnoreCaseAndAuthorIgnoreCase(request.getTitle(), request.getAuthor())) {
            throw new RuntimeException("Book with same title and author already exists");
        }

        BookCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .description(request.getDescription())
                .category(category)
                .deleted(false)
                .build();

        bookRepository.save(book);

        return mapToResponse(book);
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookUpdateRequest request) {
        Book existing = bookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (request.getTitle() != null || request.getAuthor() != null) {
            String newTitle = request.getTitle() != null ? request.getTitle() : existing.getTitle();
            String newAuthor = request.getAuthor() != null ? request.getAuthor() : existing.getAuthor();

            boolean isDuplicate = bookRepository
                    .existsByTitleIgnoreCaseAndAuthorIgnoreCase(newTitle, newAuthor);

            if (isDuplicate && (!newTitle.equalsIgnoreCase(existing.getTitle())
                    || !newAuthor.equalsIgnoreCase(existing.getAuthor()))) {
                throw new RuntimeException("Book with same title and author already exists");
            }
        }

        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getAuthor() != null) existing.setAuthor(request.getAuthor());
        if (request.getPublisher() != null) existing.setPublisher(request.getPublisher());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());

        if (request.getCategoryId() != null) {
            BookCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCategory(category);
        }

        bookRepository.save(existing);

        return mapToResponse(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return mapToResponse(book);
    }

    @Override
    public Page<BookResponse> getAll(Pageable pageable) {
        return bookRepository.findAllByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<BookResponse> search(String keyword, Pageable pageable) {
        return bookRepository.search(keyword, pageable)
                .map(this::mapToResponse);
    }
}
