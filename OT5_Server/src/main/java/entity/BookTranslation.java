package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_translations")
public class BookTranslation extends Book implements Serializable {
    @Column(name = "translate_name")
    private String translateName;
    private String language;
}
