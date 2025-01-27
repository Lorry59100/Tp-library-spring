package TP.library.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Le titre ne peut être nul")
    @NotBlank(message = "le titre ne peut pas etre vide.")
    private String title;

    @NotNull(message = "L'auteur ne peut être nul")
    @NotBlank(message = "L'auteur ne peut pas etre vide.")
    private String author;

    private Boolean isAvailable = true;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Borrowing> borrowings = new ArrayList<>();
}
