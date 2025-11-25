package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<BookCategory> findByNameIgnoreCase(String name);
}
