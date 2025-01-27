package TP.library.Service;

import TP.library.Entity.Book;
import TP.library.Entity.Borrowing;
import TP.library.Entity.User;
import TP.library.Exception.*;
import TP.library.Repository.IBookRepository;
import TP.library.Repository.IBorrowingRepository;
import TP.library.Repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    private final IBorrowingRepository borrowingRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    public BorrowingService(IBorrowingRepository borrowingRepository, IUserRepository userRepository, IBookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Borrowing borrowBook(Long userId, Long bookId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Livre non trouvé"));

        if(!book.getIsAvailable()) {
            throw new BookUnavailableException("Ce livre n'est pas disponible");
        }

        List<Borrowing> currentBorrowings = borrowingRepository.findByUserAndReturnDateIsNull(user);
        if (currentBorrowings.size() >= 3) {
            throw new BorrowingLimitExceededException("Vous ne pouvez emprunter plus de 3 livres.");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowDate(LocalDate.now());
        book.setIsAvailable(false);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public Borrowing returnBook(Long borrowingId) throws Exception {
        Borrowing borrowing = borrowingRepository.findById(borrowingId).orElseThrow(() -> new BorrowingNotFoundException("Emprunt non trouvé."));
        borrowing.setReturnDate(LocalDate.now());
        Book book = borrowing.getBook();
        book.setIsAvailable(true);
        bookRepository.save(book);
        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getUserBorrowings(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("Utilisateur inconnu"));
        return borrowingRepository.findByUserAndReturnDateIsNull(user);
    }
}
