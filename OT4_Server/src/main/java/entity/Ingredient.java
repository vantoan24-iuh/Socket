package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @Column(name = "ingredient_id")
    private String id;
    @Column(name = "ingredient_name")
    private String name;
    private String unit;
    private double price;
    private double quantity;
    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @Column(name = "supplier_name")
    private String supplierName;

    @ManyToMany
    @JoinTable(
            name = "items_ingredients",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Item> item;
}
