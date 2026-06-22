package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogCreatedDTO;
import com.dev.ednei.techFixApi.DTOS.serviceCatolog.ServiceCatalogUpdateDTO;
import com.dev.ednei.techFixApi.model.dataModeling.CatalogItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Entity
@Table(name = "service_catalog")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceCatalog extends CatalogItem {
    @OneToMany(mappedBy = "serviceCatalog")
    private List<ServiceOrderTask> serviceOrderTasks;

    public ServiceCatalog(ServiceCatalogCreatedDTO catalogDto) {
        super(catalogDto.name(), catalogDto.costPrice(), true);
    }

    public void updateCatalog(ServiceCatalogUpdateDTO catalogDto) {
        if(StringUtils.hasText(catalogDto.name())) {
            this.setName(catalogDto.name());
        }

        if(catalogDto.costPrice() != null) {
            this.setCostPrice(catalogDto.costPrice());
        }
    }

    public void disableCatalog(){
        setStatus(false);
    }

    public void activeCatalog(){
        setStatus(true);
    }
}
