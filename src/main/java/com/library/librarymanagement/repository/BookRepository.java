package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    Optional<Book> findByIdAndDeletedFalse(Long id);

    Page<Book> findAllByDeletedFalse(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
    Page<Book> search(@Param("keyword") String keyword, Pageable pageable);
}
