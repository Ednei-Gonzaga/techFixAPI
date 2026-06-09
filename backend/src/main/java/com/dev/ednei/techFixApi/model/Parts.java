package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.dataModeling.CatalogItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Parts extends CatalogItem {

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "part")
    private List<ServiceOrderItem> serviceOrderItem;
}
