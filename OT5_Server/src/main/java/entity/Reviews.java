package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {
    @Column(name = "rating")
    private int rating;
    private String comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;


}
