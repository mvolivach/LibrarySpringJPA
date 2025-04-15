package ua.volivach.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.volivach.library.models.Book;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAll(Pageable pageable);
    List<Book> findAll(Sort sort);
    List<Book> findByOwnerId(int id);
    List<Book> findByNameStartingWith(String name);
}
