package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.part.PartCreateDTO;
import com.dev.ednei.techFixApi.DTOS.part.PartUpdateDTO;
import com.dev.ednei.techFixApi.model.dataModeling.CatalogItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Parts extends CatalogItem {

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "code_sku")
    private String codeSku;

    @OneToMany(mappedBy = "part")
    private List<ServiceOrderItem> serviceOrderItem;

    public Parts(PartCreateDTO partDto, String codeSku) {
        super(partDto.name(), partDto.costPrice(), true );
        this.stockQuantity = partDto.stockQuantity();
        this.codeSku = codeSku;
    }

    public void updatePart(PartUpdateDTO partDto) {
        if(StringUtils.hasText(partDto.name())) {
            this.setName(partDto.name());
        }

        if(partDto.costPrice() != null) {
            this.setCostPrice(partDto.costPrice());
        }

        if(partDto.stockQuantity() != null) {
            this.stockQuantity = partDto.stockQuantity();
        }
    }

    public void disablePart() {
        this.setStatus(false);
    }

    public void recordQuantityUsed(Integer quantityUsed) {
        this.stockQuantity -= quantityUsed;
    }
}
