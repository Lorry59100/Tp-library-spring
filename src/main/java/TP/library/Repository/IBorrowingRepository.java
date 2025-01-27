package TP.library.Repository;

import TP.library.Entity.Borrowing;
import TP.library.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByUserAndReturnDateIsNull(User user);
}
