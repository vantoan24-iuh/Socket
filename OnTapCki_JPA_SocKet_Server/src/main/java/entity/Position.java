package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "positions")
public class Position implements Serializable {
    @Id
    @Column(name = "position_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "salary")
    private double salary;
    @Column(name = "number")
    private int number;

    public Position() {
    }

}
