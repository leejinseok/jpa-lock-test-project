package com.example.javalock.infra;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(50)")
    private String name;

    @Column(columnDefinition = "integer")
    private int inventory;

    @Version
    private int version;

    public static Product of(final Long id, final String name, final int inventory) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.inventory = inventory;
        return product;
    }

    public void updateInventory(final int inventory) {
        this.inventory = inventory;
    }
}
