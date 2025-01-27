package TP.library.Controller;

import TP.library.Entity.Borrowing;
import TP.library.Service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow")
    public Borrowing borrowBook(@RequestBody Map<String, Long> request) throws Exception {
        Long userId = request.get("userId");
        Long bookId = request.get("bookId");
        return borrowingService.borrowBook(userId, bookId);
    }

    @PostMapping("/return")
    public Borrowing returnBook(@RequestBody Map<String, Long> request) throws Exception {
        Long borrowingId = request.get("borrowingId");
        return borrowingService.returnBook(borrowingId);
    }

    @GetMapping("/current")
    public List<Borrowing> getUserBorrowings(@RequestBody Map<String, Long> request) throws Exception {
        Long userId = request.get("userId");
        return borrowingService.getUserBorrowings(userId);
    }
}
