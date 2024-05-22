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
@Table(name = "people")
public class Person implements Serializable {
    @Id
    @Column(name = "person_id")
    private String id;
    @Column(name = "full_name")
    private String name;
    private String position;
    private String phone;
    private String email;


}
