package entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("Admin")
@Entity
public class Admin extends User implements Serializable {
    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
