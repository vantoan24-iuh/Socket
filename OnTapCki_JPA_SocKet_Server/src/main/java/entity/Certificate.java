package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "certificates")
public class Certificate implements Serializable {
    @Id
    @Column(name = "certificate_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "organization")
    private String organization;
    @Column(name = "issue_date")
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    public Certificate() {
    }
}
