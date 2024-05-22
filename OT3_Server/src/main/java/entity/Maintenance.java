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
@Table(name = "maintenances")
public class Maintenance implements Serializable {
    @Id
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    private String description;
    private double cost;

    @Id
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
