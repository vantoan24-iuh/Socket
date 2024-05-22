package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate implements Serializable {
    @Id
    @Column(name = "candidate_id")
    private String id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    public Candidate() {
    }
}
