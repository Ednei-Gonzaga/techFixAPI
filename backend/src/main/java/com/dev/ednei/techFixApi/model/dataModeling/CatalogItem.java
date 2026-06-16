package com.dev.ednei.techFixApi.model.dataModeling;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "cost_price")
    private Double costPrice;

    private boolean status;

    public CatalogItem(String name, Double costPrice, boolean status) {
        this.name = name;
        this.costPrice = costPrice;
        this.status = status;
    }

}
