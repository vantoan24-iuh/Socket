package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "facilities")
public class Facility implements Serializable {
    @Id
    @Column(name = "facility_id")
    private String id;
    @Column(name = "facility_name")
    private String name;
    private String description;
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    private double price;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String location;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
