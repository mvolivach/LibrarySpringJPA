package ua.volivach.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.volivach.library.repositories.BooksRepository;
import ua.volivach.library.models.Book;
import ua.volivach.library.models.Person;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int itemsPerPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> findAll(String sortBy) {
        return booksRepository.findAll(Sort.by(sortBy));
    }

    public List<Book> findAll(int page, int itemsPerPage, String sortBy) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(sortBy))).getContent();
    }

    public List<Book> findByNameStartingWith(String name) {
        return booksRepository.findByNameStartingWith(name);
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).orElseThrow();
        book.setOwner(null);
        booksRepository.save(book);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = booksRepository.findById(id).orElseThrow();
        book.setOwner(selectedPerson);
        booksRepository.save(book);
    }

    public Optional<Person> getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner);
    }


}
