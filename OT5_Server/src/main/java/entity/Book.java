package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book implements Serializable {
    @Id
    protected String ISBN;
    protected String name;
    @Column(name = "publish_year")
    protected int publishYear;
    @Column(name = "num_of_pages")
    protected int numOfPages;
    protected double price;

    @ElementCollection
    @CollectionTable(name = "books_authors", joinColumns = @JoinColumn(name = "ISBN"))
    @Column(name = "author",nullable = false)
    protected Set<String> authors;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    protected Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @JoinColumn(name = "ISBN")
    protected Set<Reviews> reviews;


    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", publishYear=" + publishYear +
                ", numOfPages=" + numOfPages +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", reviews=" + reviews +
                '}';
    }
}

