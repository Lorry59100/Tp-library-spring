package TP.library.Service;

import TP.library.Entity.Book;
import TP.library.Exception.BookNotFoundException;
import TP.library.Exception.UserNotFoundException;
import TP.library.Repository.IBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsAvailable(bookDetails.getIsAvailable());
            return bookRepository.save(book);
        } else {
            throw new BookNotFoundException("Livre inconnu");
        }
    }

    public String deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            bookRepository.delete(book);
            return "Livre supprimé";
        } else {
            throw new BookNotFoundException("Livre inconnu");
        }
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByIsAvailable(true);
    }

    public List<Book> getBorrowedBooks() {
        return bookRepository.findByIsAvailable(false);
    }
}
