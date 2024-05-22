package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department implements Serializable {
    @Id
    @Column(name = "department_id")
    private String id;
    @Column(name = "department_name")
    private String name;
    private String location;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Person manager;
}
