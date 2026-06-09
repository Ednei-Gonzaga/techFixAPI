package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.dataModeling.CatalogItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service_catalog")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalog extends CatalogItem {
    @OneToMany(mappedBy = "serviceCatalog")
    private List<ServiceOrderTask> serviceOrderTasks;

}
